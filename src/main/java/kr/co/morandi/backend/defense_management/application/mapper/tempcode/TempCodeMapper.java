package kr.co.morandi.backend.defense_management.application.mapper.tempcode;

import kr.co.morandi.backend.defense_management.application.response.tempcode.TempCodeResponse;
import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language;
import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.TempCode;

import java.util.*;
import java.util.stream.Collectors;

public class TempCodeMapper {
    private static final Map<Language, TempCodeResponse> intialTempCodeMap =
            Arrays.stream(Language.values())
                    .collect(Collectors.toMap(
                            language -> language,
                            language -> TempCodeResponse.builder()
                                    .language(language)
                                    .code(language.getInitialCode())
                                    .build()));

    /*
     * TempCode 전체를 한 번에 반환하기 위해 initialTempCodeMap을 이용하여
     * 수집된 TempCode들로 replace하며 TempCodeResponse를 만들어 반환한다.
     * */
    public static Set<TempCodeResponse> createTempCodeResponses(Set<TempCode> tempCodes) {

        // 기본 코드를 가지고 있는 Map을 만들어서
        Map<Language, TempCodeResponse> tempCodeMap = new HashMap<>(intialTempCodeMap);

        // tempCode를 순회하면서 tempCodeMap에 해당 언어의 TempCodeResponse를 넣어준다.
        tempCodes.forEach(tempCode -> {
            tempCodeMap.replace(tempCode.getLanguage(), TempCodeResponse.builder()
                    .language(tempCode.getLanguage())
                    .code(tempCode.getCode())
                    .build());
        });

        return new HashSet<>(tempCodeMap.values());
    }

}
