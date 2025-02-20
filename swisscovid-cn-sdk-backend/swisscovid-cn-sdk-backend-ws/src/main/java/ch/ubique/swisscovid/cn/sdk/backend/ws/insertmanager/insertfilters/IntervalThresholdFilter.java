package ch.ubique.swisscovid.cn.sdk.backend.ws.insertmanager.insertfilters;

import ch.ubique.swisscovid.cn.sdk.backend.model.UserUploadPayloadOuterClass;
import ch.ubique.swisscovid.cn.sdk.backend.model.UserUploadPayloadOuterClass.UploadVenueInfo;
import ch.ubique.swisscovid.cn.sdk.backend.ws.insertmanager.InsertException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The app uploads VenueInfo objects that span at most 1 hour.
 * UploadVenueInfo objects that break the interval threshold of (0,1) are dropped.
 */
public class IntervalThresholdFilter implements UploadInsertionFilter {
  @Override
  public List<UserUploadPayloadOuterClass.UploadVenueInfo> filter(
      LocalDateTime now,
      List<UploadVenueInfo> uploadVenueInfoList,
      Object principal)
      throws InsertException {
    return uploadVenueInfoList.stream().filter(uploadVenueInfo -> {
        final var start = uploadVenueInfo.getIntervalStartMs();
        final var end = uploadVenueInfo.getIntervalEndMs();
        final var notNegative = end - start > 0;
        final var lessThan1h = end - start <= 60 * 60 * 1000;
        return (notNegative && lessThan1h);
    }).collect(Collectors.toList());
  }
}
