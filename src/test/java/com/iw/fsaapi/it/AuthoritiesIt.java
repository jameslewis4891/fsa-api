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

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class AuthoritiesIt {

    private static final int WIRE_MOCK_PORT = 8089;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(WIRE_MOCK_PORT);

    @Test
    public void shouldReturnAListOfAuthorities() throws Exception {
        //arrange
        stubGoodAuthoritiesResponse();

        //act
        final ResponseEntity<String> responseEntity = testRestTemplate.getForEntity("/authorities", String.class);

        //assert
        assertThat(responseEntity.getStatusCode(),is(HttpStatus.OK));
        assertThat(responseEntity.getHeaders().get("content-type").get(0),is(MediaType.APPLICATION_JSON_UTF8_VALUE));
        assertThat(responseEntity.getBody().contains("Aberdeen City"),is(true));
        assertThat(responseEntity.getBody().contains("Aberdeenshire"),is(true));
        assertThat(responseEntity.getBody().contains("Adur"),is(true));
    }

    @Test
    public void shouldReturnA500ErrorAndAnErrorMessageIfFSAAuthoritiesIsUnreachable() throws Exception {
        //arrange
        stubErrorResponseFromFSAuthoritiesCall();

        //act
        final ResponseEntity<String> responseEntity = testRestTemplate.getForEntity("/authorities", String.class);

        //assert
        assertThat(responseEntity.getStatusCode(),is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThat(responseEntity.getHeaders().get("content-type").get(0),is(MediaType.APPLICATION_JSON_VALUE));
        assertThat(responseEntity.getBody().contains("Invalid http response code 500"),is(true));
    }

    private void stubGoodAuthoritiesResponse() throws Exception {
        stubFor(WireMock.get(urlEqualTo("/Authorities/basic"))
                .willReturn(aResponse()
                .withHeader("Content-type","application/json")
                .withBody(getFileContent("authoritiesResponse.json"))));
    }

    private void stubErrorResponseFromFSAuthoritiesCall() throws Exception {
        stubFor(WireMock.get(urlEqualTo("/Authorities/basic"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())));
    }

    private String getFileContent(final String filePath) throws IOException {
        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(classLoader.getResource(filePath).getFile());

        return FileUtils.readFileToString(file,Charset.forName("UTF-8"));
    }

}
