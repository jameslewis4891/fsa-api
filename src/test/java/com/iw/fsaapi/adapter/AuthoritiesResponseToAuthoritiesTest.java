package com.iw.fsaapi.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iw.fsaapi.response.Authority;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthoritiesResponseToAuthoritiesTest {

    @Spy
    private ObjectMapper objectMapper;

    @InjectMocks
    private AuthoritiesResponseToAuthorities authoritiesResponseToAuthorities;

    @Test
    public void shouldReturnAListOfAuthorities() throws Exception {
        //arrange
        final ResponseEntity<String> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(getFileContent("authoritiesResponse.json"));

        //act
        List<Authority> authorityList = authoritiesResponseToAuthorities.apply(responseEntity);

        //assert
        assertThat(authorityList.size(),is(3));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowIllegalStateExceptionIfAuthoritiesCannotBeRead() throws Exception {
        //arrange
        final ResponseEntity<String> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn("{UNEXPECTED CONTENT}");

        //act
        authoritiesResponseToAuthorities.apply(responseEntity);

        //assert
    }

    private String getFileContent(final String filePath) throws IOException {
        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(classLoader.getResource(filePath).getFile());

        return FileUtils.readFileToString(file,Charset.forName("UTF-8"));
    }
}