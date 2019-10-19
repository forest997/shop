package com.wuyou.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.wuyou.entity.Goods;
import com.wuyou.feign.GoodsFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author Forest
 * @Date 2019/10/10
 */
@Controller
@RequestMapping("/goodsManager")
public class GoodsController {
    @Autowired
    private GoodsFeign goodsFeign;
    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    private String uploadPath = "E:\\imgs";

    @RequestMapping("/list")
    public String list(ModelMap map) {
        List<Goods> goodsList = goodsFeign.list();
        map.put("goodsList", goodsList);
        System.out.println("查询了所有商品信息:" + goodsList);
        return "goodslist";
    }

    @ResponseBody
    @RequestMapping("/uploader")
    public String uploader(MultipartFile file, HttpServletResponse response) {
        File outfile = new File(uploadPath);
        if (!outfile.exists()) {
            if (!outfile.mkdirs()) {
                throw new RuntimeException("图片上传路径不存在且创建失败");
            }
        }
        System.out.println("收到了图片:"+file.getOriginalFilename());
        String filename = UUID.randomUUID().toString();
        outfile = new File(uploadPath, filename);
        String uploadPath=null;
        try {
            StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(
                    file.getInputStream(),
                    file.getSize(),
                    "JPG",
                    null
            );
            uploadPath = storePath.getFullPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        uploadPath = "http://192.168.138.200:8080/" + uploadPath;
//
//        try (
//                InputStream in = file.getInputStream();
//                OutputStream out = new FileOutputStream(outfile);
//        ) {
//            IOUtils.copy(in, out);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return "{\"filename\":\"" + uploadPath + "\"}";
    }

//    @RequestMapping("/showImg")
//    public void showImg(String filename, HttpServletResponse response) {
//        System.out.println("回显图片:" + filename);
//        File file = new File(uploadPath, filename);
//        try (
//                InputStream in = new FileInputStream(file);
//                ServletOutputStream out = response.getOutputStream();
//        ) {
//            IOUtils.copy(in, out);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    @RequestMapping("/insert")
    public String insert(Goods goods) {
        boolean result = goodsFeign.insert(goods);
        return result ? "redirect:http://localhost:16666/back/goodsManager/list" : "error";
    }


}
