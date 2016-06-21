package es.gobcan.istac.search.web.client.utils;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;

import com.smartgwt.client.widgets.grid.ListGridRecord;

import es.gobcan.istac.search.core.dto.RecommendedKeywordDto;
import es.gobcan.istac.search.core.dto.RecommendedLinkDto;
import es.gobcan.istac.search.web.client.model.RecommendedKeywordRecord;
import es.gobcan.istac.search.web.client.model.RecommendedLinkRecord;
import es.gobcan.istac.search.web.client.model.ds.RecommendedKeywordDS;

public class RecordUtils {

    private RecordUtils() {
    };

    public static RecommendedLinkRecord[] getRecommendedLinkListRecords(List<RecommendedLinkDto> recommendedLinkList) {
        List<RecommendedLinkRecord> records = new ArrayList<RecommendedLinkRecord>();
        for (RecommendedLinkDto dto : recommendedLinkList) {
            records.add(getRecommendedLinkRecord(dto));
        }
        return records.toArray(new RecommendedLinkRecord[records.size()]);
    }

    public static RecommendedKeywordRecord[] getRecommendedKeywordListRecords(List<RecommendedKeywordDto> recommendedKeywordList) {
        List<RecommendedKeywordRecord> records = new ArrayList<RecommendedKeywordRecord>();
        for (RecommendedKeywordDto dto : recommendedKeywordList) {
            records.add(getRecommendedKeywordRecord(dto));
        }
        return records.toArray(new RecommendedKeywordRecord[records.size()]);
    }

    public static RecommendedLinkRecord getRecommendedLinkRecord(RecommendedLinkDto recommendedLinkDto) {
        RecommendedLinkRecord record = new RecommendedLinkRecord();
        record.setRecommendedLinkDto(recommendedLinkDto);

        record.setUrl(recommendedLinkDto.getUrl());
        record.setTitle(recommendedLinkDto.getTitle());
        record.setDescription(recommendedLinkDto.getDescription());
        record.setRecommendedKeywordId(recommendedLinkDto.getRecommendedKeyword().getId());

        // For visualization
        record.setRecommendedKeywordKeyword(recommendedLinkDto.getRecommendedKeyword().getKeyword());
        record.setRecommendedKeywordCategory(recommendedLinkDto.getRecommendedKeyword().getCategory());
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

    public static List<Long> getRecommendedKeywordListIds(ListGridRecord[] recommendedKeywordRecordList) {
        List<Long> ids = new ArrayList<Long>();
        List<RecommendedKeywordDto> recommendedKeywordListDto = getRecommendedKeywordListDto(recommendedKeywordRecordList);
        for (RecommendedKeywordDto dto : recommendedKeywordListDto) {
            ids.add(dto.getId());
        }
        return ids;
    }

    public static List<RecommendedKeywordDto> getRecommendedKeywordListDto(ListGridRecord[] selectedRecords) {
        List<RecommendedKeywordDto> dtos = new ArrayList<RecommendedKeywordDto>();
        for (ListGridRecord record : selectedRecords) {
            dtos.add(((RecommendedKeywordRecord) record).getRecommendedKeywordDto());
        }
        return dtos;
    }

    public static RecommendedKeywordDto getRecommendedKeywordDto(CustomDynamicForm form) {
        RecommendedKeywordDto dto = (RecommendedKeywordDto) form.getValue(RecommendedKeywordDS.RECOMMENDED_KEYWORD_DTO);
        if (dto == null) {
            dto = new RecommendedKeywordDto();
        }
        dto.setKeyword(form.getValueAsString(RecommendedKeywordDS.KEYWORD));
        dto.setCategory(form.getValueAsExternalItemDto(RecommendedKeywordDS.CATEGORY));
        return dto;
    }

    public static RecommendedKeywordRecord getRecommendedKeywordRecord(RecommendedKeywordDto recommendedKeywordDto) {
        RecommendedKeywordRecord record = new RecommendedKeywordRecord();
        record.setRecommendedKeywordDto(recommendedKeywordDto);

        record.setKeyword(recommendedKeywordDto.getKeyword());
        record.setCategory(recommendedKeywordDto.getCategory());
        return record;
    }

}
