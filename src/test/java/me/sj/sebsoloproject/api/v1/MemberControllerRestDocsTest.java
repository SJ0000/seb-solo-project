package me.sj.sebsoloproject.api.v1;


import com.fasterxml.jackson.databind.ObjectMapper;
import me.sj.sebsoloproject.entity.CompanyLocation;
import me.sj.sebsoloproject.entity.CompanyType;
import me.sj.sebsoloproject.entity.Member;
import me.sj.sebsoloproject.repository.CompanyRepository;
import me.sj.sebsoloproject.repository.MemberRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.restdocs.request.RequestParametersSnippet;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
class MemberControllerRestDocsTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CompanyRepository companyRepository;

    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @Transactional
    void getMembersTest() throws Exception {
        // given
        CompanyType manufacturing = new CompanyType("제조업");
        CompanyType distribution = new CompanyType("유통업");
        CompanyType it = new CompanyType("IT");
        companyRepository.save(manufacturing, distribution, it);

        CompanyLocation seoul = new CompanyLocation("서울");
        CompanyLocation busan = new CompanyLocation("부산");
        companyRepository.save(seoul, busan);

        memberRepository.save(new Member("member1", "1111", Member.Gender.FEMALE, "Company01", manufacturing, seoul));
        memberRepository.save(new Member("member2", "2222", Member.Gender.MALE, "Company02", distribution, seoul));
        memberRepository.save(new Member("member3", "3333", Member.Gender.MALE, "Company03", it, seoul));
        memberRepository.save(new Member("member4", "4444", Member.Gender.FEMALE, "Company04", it, busan));
        memberRepository.save(new Member("member5", "5555", Member.Gender.MALE, "Company05", manufacturing, busan));

        // expected
        ResultActions result = mockMvc.perform(get("/v1/members")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").isString())
                .andExpect(jsonPath("$[0].gender").isString())
                .andExpect(jsonPath("$[0].companyName").isString())
                .andExpect(jsonPath("$[0].companyType").isString())
                .andExpect(jsonPath("$[0].companyLocation").isString())
                .andDo(print());

        // docs
        result
                .andDo(document(
                        "get-members",
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("type").description("업종").optional(),
                                parameterWithName("location").description("지역").optional()
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("회원 식별 번호"),
                                        fieldWithPath("[].name").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("[].gender").type(JsonFieldType.STRING).description("회원 성별"),
                                        fieldWithPath("[].companyName").type(JsonFieldType.STRING).description("회사 이름"),
                                        fieldWithPath("[].companyType").type(JsonFieldType.STRING).description("업종"),
                                        fieldWithPath("[].companyLocation").type(JsonFieldType.STRING).description("지역")
                                        )
                        )
                ));
    }
}