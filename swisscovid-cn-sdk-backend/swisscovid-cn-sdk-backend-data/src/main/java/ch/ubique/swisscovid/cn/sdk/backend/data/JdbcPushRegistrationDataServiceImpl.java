package ch.ubique.swisscovid.cn.sdk.backend.data;

import ch.ubique.swisscovid.cn.sdk.backend.model.PushRegistrationOuterClass.PushRegistration;
import ch.ubique.swisscovid.cn.sdk.backend.model.PushRegistrationOuterClass.PushType;
import java.util.List;
import javax.sql.DataSource;
import org.apache.logging.log4j.util.Strings;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.annotation.Transactional;

public class JdbcPushRegistrationDataServiceImpl implements PushRegistrationDataService {

    private final NamedParameterJdbcTemplate jt;
    private final SimpleJdbcInsert pushRegistrationInsert;

    public JdbcPushRegistrationDataServiceImpl(final DataSource dataSource) {
        this.jt = new NamedParameterJdbcTemplate(dataSource);
        this.pushRegistrationInsert =
                new SimpleJdbcInsert(dataSource)
                        .withTableName("t_push_registration")
                        .usingGeneratedKeyColumns("pk_push_registration_id");
    }

    @Override
    @Transactional
    public void upsertPushRegistration(final PushRegistration pushRegistration) {
        if (Strings.isBlank(pushRegistration.getPushToken())) {
            deletePushRegistration(pushRegistration.getDeviceId());
        }

        final var pushRegistrationParams = getPushRegistrationParams(pushRegistration);
        deleteDuplicates(pushRegistrationParams);
        pushRegistrationInsert.execute(pushRegistrationParams);
    }

    private void deletePushRegistration(final String deviceId) {
        final var sql = "delete from t_push_registration where device_id = :device_id";
        jt.update(sql, new MapSqlParameterSource("device_id", deviceId));
    }

    private void deleteDuplicates(final MapSqlParameterSource pushRegistrationParams) {
        final var sql =
                "delete from t_push_registration where device_id = :device_id or push_token = :push_token";
        jt.update(sql, pushRegistrationParams);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PushRegistration> getPushRegistrationByType(final PushType pushType) {
        final String sql = "select * from t_push_registration where push_type = :push_type";
        final var params = new MapSqlParameterSource("push_type", pushType.name());
        return jt.query(sql, params, new PushRegistrationRowMapper());
    }

    private MapSqlParameterSource getPushRegistrationParams(PushRegistration pushRegistration) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("push_type", pushRegistration.getPushType().name());
        params.addValue("push_token", pushRegistration.getPushToken());
        params.addValue("device_id", pushRegistration.getDeviceId());
        return params;
    }

	@Override
	@Transactional(readOnly = false)
	public void removeRegistrations(List<String> tokensToRemove) {
		if (tokensToRemove != null && !tokensToRemove.isEmpty()) {
			jt.update("delete from t_push_registration where push_token in (:tokensToRemove)", new MapSqlParameterSource("tokensToRemove", tokensToRemove));
		}
	}
}
