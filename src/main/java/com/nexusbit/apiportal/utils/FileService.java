package com.nexusbit.apiportal.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    public String base64ToUrl(String file){
        return file;
    }
}
