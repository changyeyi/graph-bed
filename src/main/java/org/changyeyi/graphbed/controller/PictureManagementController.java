package org.changyeyi.graphbed.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @author :    zj.wu
 * @date :      2019/08/28 10:56
 */
@RestController
@RequestMapping("/picture")
public class PictureManagementController {

    @Value("${warehouse.picture.root}")
    private String pictureRoot;
    @Value("${prefix.picture}")
    private String hostPrefix;
    private DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy/MM/dd");

    @PostMapping
    public String savePicture(@RequestParam("file") MultipartFile file) throws IOException {
        //确认图片存储文件夹目录
        String dateStr = formatter.format(LocalDate.now());
        File root=new File(pictureRoot+"/"+dateStr);
        if(!root.exists()){
            root.mkdirs();
        }
        //生成随机文件名
        String pictureSuffix="jpg";
        String[] split = file.getName().split(".");
        if(split.length==2){
            pictureSuffix=split[1];
        }
        String pictureName=uuid()+"."+pictureSuffix;
        File picture = new File(root, pictureName);
        file.transferTo(picture);
        return hostPrefix+picture.getAbsolutePath().replace("\\","/").replace(pictureRoot,"");
    }

    private String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
