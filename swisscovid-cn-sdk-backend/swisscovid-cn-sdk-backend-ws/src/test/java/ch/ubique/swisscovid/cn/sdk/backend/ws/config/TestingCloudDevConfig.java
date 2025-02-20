/*
 * Copyright (c) 2020 Ubique Innovation AG <https://www.ubique.ch>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package ch.ubique.swisscovid.cn.sdk.backend.ws.config;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@Profile("test-cloud")
public class TestingCloudDevConfig extends WSCloudBaseConfig {

	@Bean
	@Override
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).build();
	}

	@Bean
	@Override
	public Flyway flyway() {
		Flyway flyWay = Flyway.configure().dataSource(dataSource()).locations("classpath:/db/migration/hsqldb").load();
		flyWay.migrate();
		return flyWay;
	}

	@Override
	public String getDbType() {
		return "hsqldb";
	}

	@Override
	String getSignaturePublicKey() {
		return "LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tCk1JSUNTRENDQWU2Z0F3SUJBZ0lVS3pEQlJIZlZVN2djRGZxUTBGUDFLTkJULzVJd0NnWUlLb1pJemowRUF3SXcKZXpFTE1Ba0dBMVVFQmhNQ1EwZ3hEVEFMQmdOVkJBZ01CRUpsY200eERUQUxCZ05WQkFjTUJFSmxjbTR4RERBSwpCZ05WQkFvTUEwSkpWREVNTUFvR0ExVUVDd3dEUlZkS01RMHdDd1lEVlFRRERBUlVaWE4wTVNNd0lRWUpLb1pJCmh2Y05BUWtCRmhSemRYQndiM0owUUdKcGRDNWhaRzFwYmk1amFEQWVGdzB5TURBME1qZ3hNakUwTlRGYUZ3MHkKTVRBME1qZ3hNakUwTlRGYU1Ic3hDekFKQmdOVkJBWVRBa05JTVEwd0N3WURWUVFJREFSQ1pYSnVNUTB3Q3dZRApWUVFIREFSQ1pYSnVNUXd3Q2dZRFZRUUtEQU5DU1ZReEREQUtCZ05WQkFzTUEwVlhTakVOTUFzR0ExVUVBd3dFClZHVnpkREVqTUNFR0NTcUdTSWIzRFFFSkFSWVVjM1Z3Y0c5eWRFQmlhWFF1WVdSdGFXNHVZMmd3VmpBUUJnY3EKaGtqT1BRSUJCZ1VyZ1FRQUNnTkNBQVRhc0ZoMEdLZWs1WTRKdXdnaTVIOEFrL3FmamtKQ3d6N1BYb0lVZWJnaQpzeTdUVlFMckltQlBVN2lnMDM3azBkb1V4aytYZEJLWDQzdi9yZEdZVUtmMW8xTXdVVEFkQmdOVkhRNEVGZ1FVCnVTS2lWSUdsRnpQdDdXd3Z1VGNicDNrckQ0UXdId1lEVlIwakJCZ3dGb0FVdVNLaVZJR2xGelB0N1d3dnVUY2IKcDNrckQ0UXdEd1lEVlIwVEFRSC9CQVV3QXdFQi96QUtCZ2dxaGtqT1BRUURBZ05JQURCRkFpQkRteEJUQ3BZawphN0hFeUFEWnN4d3p3b2h0TjBwNTd5QllMYjZzQ3B3ODhBSWhBSXpTUDdCV0tGWmNDSmI5ZmhwcjZaTXpJd0tlCkhhSWpIK2E4elV2Nk1PaW8KLS0tLS1FTkQgQ0VSVElGSUNBVEUtLS0tLQo=";
	}

	@Override
	String getSignaturePrivateKey() {
		return "LS0tLS1CRUdJTiBQUklWQVRFIEtFWS0tLS0tCk1JR0VBZ0VBTUJBR0J5cUdTTTQ5QWdFR0JTdUJCQUFLQkcwd2F3SUJBUVFnMkRsai9lNW5rRlBtTk1MVjd1NjQKenFuOHdSeVgrUTgyc045RDRSWXlvNjJoUkFOQ0FBVGFzRmgwR0tlazVZNEp1d2dpNUg4QWsvcWZqa0pDd3o3UApYb0lVZWJnaXN5N1RWUUxySW1CUFU3aWcwMzdrMGRvVXhrK1hkQktYNDN2L3JkR1lVS2YxCi0tLS0tRU5EIFBSSVZBVEUgS0VZLS0tLS0K";
	}
}
