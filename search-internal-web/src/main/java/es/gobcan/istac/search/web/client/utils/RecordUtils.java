package es.gobcan.istac.search.web.client.utils;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.widgets.grid.ListGridRecord;

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.core.dto.RecommendedLinkDto;
import es.gobcan.istac.search.web.client.model.RecommendedKeywordRecord;
import es.gobcan.istac.search.web.client.model.RecommendedLinkRecord;

public class RecordUtils {

    public static RecommendedLinkRecord[] getRecommendedLinkListRecords(List<RecommendedLinkDto> recommendedLinkList) {
        List<RecommendedLinkRecord> records = new ArrayList<RecommendedLinkRecord>();
        for (RecommendedLinkDto dto : recommendedLinkList) {
            records.add(getRecommendedLinkRecord(dto));
        }
        return records.toArray(new RecommendedLinkRecord[records.size()]);
    }

    public static RecommendedLinkRecord getRecommendedLinkRecord(RecommendedLinkDto recommendedLinkDto) {
        RecommendedLinkRecord record = new RecommendedLinkRecord();
        record.setRecommendedLinkDto(recommendedLinkDto);

        record.setUrl(recommendedLinkDto.getUrl());
        record.setTitle(recommendedLinkDto.getTitle());
        record.setDescription(recommendedLinkDto.getDescription());
        record.setRecommendedKeywordId(recommendedLinkDto.getRecommendedKeyword().getId());
        record.setRecommendedKeywordKeyword(recommendedLinkDto.getRecommendedKeyword().getKeyword()); // For visualization
        return record;
    }

    public static RecommendedLinkDto getRecommendedLinkDto(RecommendedLinkRecord recommendedLinkRecord, List<RecommendedKeywordDto> recommendedKeywordListDto) {
        RecommendedLinkDto dto = recommendedLinkRecord.getRecommendedLinkdDto();

        dto.setUrl(recommendedLinkRecord.getUrl());
        dto.setTitle(recommendedLinkRecord.getTitle());
        dto.setDescription(recommendedLinkRecord.getDescription());
        dto.setRecommendedKeyword(getRecommendedKeywordById(recommendedLinkRecord.getRecommendedKeywordId(), recommendedKeywordListDto));
        return dto;
    }

    private static RecommendedKeywordDto getRecommendedKeywordById(Long id, List<RecommendedKeywordDto> recommendedKeywordListDto) {
        for (RecommendedKeywordDto recommendedKeywordDto : recommendedKeywordListDto) {
            if (recommendedKeywordDto.getId().equals(id)) {
                return recommendedKeywordDto;
            }
        }
        return new RecommendedKeywordDto();
    }

    public static List<Long> getRecommendedLinkListIds(ListGridRecord[] recommendedLinkRecordList) {
        List<Long> ids = new ArrayList<Long>();
        for (ListGridRecord record : recommendedLinkRecordList) {
            ids.add(((RecommendedLinkRecord) record).getRecommendedLinkdDto().getId());
        }
        return ids;
    }

    public static RecommendedKeywordDto getRecommendedKeywordDto(RecommendedKeywordRecord recommendedKeywordRecord) {
        RecommendedKeywordDto dto = recommendedKeywordRecord.getRecommendedKeywordDto();

        dto.setKeyword(recommendedKeywordRecord.getKeyword());
        return dto;
    }

    public static RecommendedKeywordRecord getRecommendedKeywordRecord(RecommendedKeywordDto recommendedKeywordDto) {
        RecommendedKeywordRecord record = new RecommendedKeywordRecord();
        record.setRecommendedKeywordDto(recommendedKeywordDto);

        record.setKeyword(recommendedKeywordDto.getKeyword());
        return record;
    }

}
