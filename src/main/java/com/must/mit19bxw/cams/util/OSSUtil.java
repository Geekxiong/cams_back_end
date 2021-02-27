package com.must.mit19bxw.cams.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.must.mit19bxw.cams.bean.OSSConfig;
import com.must.mit19bxw.cams.bean.OSSTempLicense;

import java.util.Date;

public class OSSUtil {

    private static String endpoint = OSSConfig.endpoint;
    private static String bucketName = OSSConfig.bucketName;
    private static String stsEndpoint = OSSConfig.stsEndpoint;
    private static String accessKeyId = OSSConfig.accessKeyId;
    private static String accessKeySecret = OSSConfig.accessKeySecret;
    private static String roleArn =  "acs:ram::"+OSSConfig.accountID+":role/"+OSSConfig.RAMRoleName;
    private static String roleSessionName = "alicexxx";

    /**
     * 获取临时密钥
     * @param userType
     * @param licenseType
     * @param userId
     * @return
     */
    public static OSSTempLicense getTemporaryLicense(String userType, String licenseType, Integer userId){
        String policy="";
        if(userType.equals("student")){
            if(licenseType.toUpperCase().equals("PUT")){
                policy = OSSConfig.getStudentPutOnlyPolicy(userId);
            }else {
                policy = OSSConfig.getStudentReadOnlyPolicy(userId);
            }
        }else if(userType.equals("teacher")){
            if(licenseType.toUpperCase().equals("PUT")){
                policy = OSSConfig.getTeacherPutOnlyPolicy(userId);
            }else {
                policy = OSSConfig.getTeacherReadOnlyPolicy();
            }
        }else {
            return null;
        }
        System.out.println(policy);
        try {
            DefaultProfile.addEndpoint("", "", "Sts", stsEndpoint);
            IClientProfile profile = DefaultProfile.getProfile("", accessKeyId, accessKeySecret);
            DefaultAcsClient client = new DefaultAcsClient(profile);
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setMethod(MethodType.POST);
            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
            request.setPolicy(policy); // 若policy为空，则用户将获得该角色下所有权限
            request.setDurationSeconds(900L); // 设置凭证有效时间
            final AssumeRoleResponse response = client.getAcsResponse(request);
            OSSTempLicense ossTempLicense = new OSSTempLicense();
            ossTempLicense.setRegion(OSSConfig.endpoint);
            ossTempLicense.setBucket(OSSConfig.bucketName);
            ossTempLicense.setAccessKeyId(response.getCredentials().getAccessKeyId());
            ossTempLicense.setAccessKeySecret(response.getCredentials().getAccessKeySecret());
            ossTempLicense.setSecurityToken(response.getCredentials().getSecurityToken());
            ossTempLicense.setExpiration(CommonUtil.dateStr2DateUTC(response.getCredentials().getExpiration()));
            return ossTempLicense;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除需要详细的权限鉴别，这里还没做
     * @param filePath
     * @return
     */
    public static void deleteFile(String filePath){
        if(filePath.charAt(0)=='/'||filePath.charAt(0)=='\\'){
            filePath=filePath.substring(1);
        }
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.deleteObject(bucketName, filePath);
        ossClient.shutdown();
    }

    public static Boolean checkTempLicenseAlive(OSSTempLicense tempLicense){
        if(tempLicense==null){
            return false;
        }
        Date now = new Date();
        if(tempLicense.getExpiration().getTime()-now.getTime()<5*1000){
            return false;
        }
        return true;
    }


}
