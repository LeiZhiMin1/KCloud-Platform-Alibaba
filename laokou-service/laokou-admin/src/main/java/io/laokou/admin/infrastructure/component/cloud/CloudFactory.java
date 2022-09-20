package io.laokou.admin.infrastructure.component.cloud;
import io.laokou.admin.infrastructure.component.handler.impl.AdminHandler;
import io.laokou.admin.infrastructure.config.CloudStorageConfig;
import io.laokou.admin.infrastructure.common.enums.CloudTypeEnum;
import io.laokou.common.exception.CustomException;
import io.laokou.common.utils.JacksonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * @author Kou Shenhai
 */
@Component
public class CloudFactory {

    @Autowired
    private AdminHandler adminHandler;

   public  AbstractCloudStorageService build(){
       String oss = adminHandler.getOss();
       if (StringUtils.isBlank(oss)){
           throw new CustomException("请配置OSS");
       }
       CloudStorageConfig config = JacksonUtil.toBean(oss, CloudStorageConfig.class);
       if (CloudTypeEnum.ALIYUN.ordinal() == config.getType()){
           return new AliyunCloudStorageService(config);
       }
       else if (CloudTypeEnum.LOCAL.ordinal() == config.getType()){
           return new LocalCloudStorageService(config);
       }
       else if (CloudTypeEnum.FASTDFS.ordinal() == config.getType()){
           return new FastDFSCloudStorageService(config);
       }
       throw new CustomException("请检查OSS相关配置");
   }

}
