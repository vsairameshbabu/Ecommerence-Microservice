package com.info.democonfig;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class BuildController {
//    @Value("${build.id:default}")
//    private String buildId;
//
//    @Value("${build.version:default}")
//    private String buildVersion;
//
//    @Value("${build.name:default}")
//    private String buildName;
//
//    @Value("${build.type:default}")
//    private String buildType;

    private BuildInfo buildInfo;

    @GetMapping("/build")
    public String getBuild() {
        return "Build ID: " + buildInfo.getId() + "\n" +
                "Build Version: " + buildInfo.getVersion() + "\n" +
                "Build Name: " + buildInfo.getName() + "\n" +
                "Build Type: " + buildInfo.getType() + "\n";
    }

//    @GetMapping(value = "/build", produces = "text/plain")
//    public String getBuild() {
//        return "Build ID: " + buildInfo.getId() + "\n" +
//                "Build Version: " + buildInfo.getVersion() + "\n" +
//                "Build Name: " + buildInfo.getName() + "\n" +
//                "Build Type: " + buildInfo.getType() + "\n" ;
//    }


//    @GetMapping("/build")
//    public Map<String, String> getBuild() {
//        Map<String, String> response = new LinkedHashMap<>();
//        response.put("Build ID", buildInfo.getId());
//        response.put("Build Version", buildInfo.getVersion());
//        response.put("Build Name", buildInfo.getName());
//        response.put("Build Type", buildInfo.getType());
//        return response;
//    }

}
