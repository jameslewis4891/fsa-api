package com.iw.fsaapi.it;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class EstablishmentsIt {

    private static final int WIRE_MOCK_PORT = 8089;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(WIRE_MOCK_PORT);

    @Test
    public void shouldReturnAListOfPercentageRatings() throws Exception {
        //arrange
        final Integer authorityId = 392;
        stubGoodAuthorityResponse(authorityId);
        stubGoodEstablishmentsRatingsResponse(authorityId);

        //act
        final ResponseEntity<String> responseEntity = testRestTemplate.getForEntity("/establishments/ratings/byAuthority/"+authorityId, String.class);

        //assert
        assertThat(responseEntity.getStatusCode(),is(HttpStatus.OK));
        assertThat(responseEntity.getHeaders().get("content-type").get(0),is(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void shouldReturnBadRequestWhenAuthorityIsNotFound() {
        //arrange
        final Integer authorityId = 392;
        stubNotFoundAuthorityResponse(authorityId);

        //act
        final ResponseEntity<String> responseEntity = testRestTemplate.getForEntity("/establishments/ratings/byAuthority/"+authorityId, String.class);

        assertThat(responseEntity.getStatusCode(),is(HttpStatus.BAD_REQUEST));
        assertThat(responseEntity.getBody().contains(String.format("Authority with id %s could not be found",authorityId)),is(true));
    }

    @Test
    public void shouldReturnA500BadRequestIfFSAEstablishmentsIsUnreachable() throws Exception {
        //arrange
        final Integer authorityId = 392;
        stubGoodAuthorityResponse(authorityId);
        stubErrorFromEstablishmentsRatingsResponse(authorityId);

        //act
        final ResponseEntity<String> responseEntity = testRestTemplate.getForEntity("/establishments/ratings/byAuthority/"+authorityId, String.class);

        assertThat(responseEntity.getStatusCode(),is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void shouldReturnA500BadRequestIfAnFSAAuthorityIsUnreachable() {
        //arrange
        final Integer authorityId = 392;
        stubErrorResponseFromFSAuthorityCall(authorityId);
        //act
        final ResponseEntity<String> responseEntity = testRestTemplate.getForEntity("/establishments/ratings/byAuthority/"+authorityId, String.class);

        assertThat(responseEntity.getStatusCode(),is(HttpStatus.INTERNAL_SERVER_ERROR));
    }


    private void stubNotFoundAuthorityResponse(final Integer authorityId){
        stubFor(WireMock.get(urlEqualTo("/Authorities/"+authorityId))
                .willReturn(aResponse()
                        .withHeader("Content-type","application/json")
                        .withStatus(HttpStatus.NOT_FOUND.value())));
    }


    private void stubGoodAuthorityResponse(final Integer authorityId) throws Exception {
        stubFor(WireMock.get(urlEqualTo("/Authorities/"+authorityId))
                .willReturn(aResponse()
                        .withHeader("Content-type","application/json")
                        .withBody(getFileContent("authorityResponse.json"))));
    }

    private void stubErrorResponseFromFSAuthorityCall(final Integer authorityId){
        stubFor(WireMock.get(urlEqualTo("/Authorities/"+authorityId))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())));
    }

    private void stubErrorFromEstablishmentsRatingsResponse(final Integer authorityId) {
        stubFor(WireMock.get(urlEqualTo("/Establishments?localAuthorityId="+authorityId+"&pageSize=0"))
                .willReturn(aResponse()
                        .withHeader("Content-type","application/json")
                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())));
    }

    private void stubGoodEstablishmentsRatingsResponse(final Integer authorityId) throws Exception {
        stubFor(WireMock.get(urlEqualTo("/Establishments?localAuthorityId="+authorityId+"&pageSize=0"))
                .willReturn(aResponse()
                        .withHeader("Content-type","application/json")
                        .withBody(getFileContent("establishmentsResponse.json"))));
    }

    private String getFileContent(final String filePath) throws IOException {
        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(Objects.requireNonNull(classLoader.getResource(filePath)).getFile());

        return FileUtils.readFileToString(file,Charset.forName("UTF-8"));
    }
}
