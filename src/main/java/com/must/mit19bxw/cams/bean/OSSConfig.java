package com.must.mit19bxw.cams.bean;

public class OSSConfig {
    public static String endpoint = "oss-cn-shenzhen.aliyuncs.com";
    public static String stsEndpoint = "sts.aliyuncs.com";
    public static String accessKeyId = "youraccesskeyid";  // 替换成你自己的
    public static String accessKeySecret = "youraccesskeysecret";  // 替换成你自己的
    public static String bucketName = "yourbucketname";  // 替换成你自己的
    public static String accountID = "youraccountid";  // 替换成你自己的
    public static String RAMRoleName = "OSS-Visitor";

    public static String getStudentReadOnlyPolicy(Integer studentId){
        String studentReadOnlyPolicy = "{\n" +
                "    \"Version\": \"1\", \n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Action\": [\n" +
                "                \"oss:GetObject\"\n" +
                "            ], \n" +
                "            \"Resource\": [\n" +
                "                \"acs:oss:*:*:"+bucketName+"/courseware/*\", \n" +
                "                \"acs:oss:*:*:"+bucketName+"/assignmentAnnex/*\", \n" +
                "                \"acs:oss:*:*:"+bucketName+"/assignmentSubmission/student_"+studentId+"/*\" \n" +
                "            ], \n" +
                "            \"Effect\": \"Allow\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        return studentReadOnlyPolicy;
    }

    public static String getStudentPutOnlyPolicy(Integer studentId){
        String studentPutOnlyPolicy = "{\n" +
                "    \"Version\": \"1\", \n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Action\": [\n" +
                "                \"oss:PutObject\"\n" +
                "            ], \n" +
                "            \"Resource\": [\n" +
                "                \"acs:oss:*:*:"+bucketName+"/assignmentSubmission/student_"+studentId+"/*\" \n" +
                "            ], \n" +
                "            \"Effect\": \"Allow\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        return studentPutOnlyPolicy;
    }

    public static String getTeacherReadOnlyPolicy(){
        String teacherReadOnlyPolicy = "{\n" +
                "    \"Version\": \"1\", \n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Action\": [\n" +
                "                \"oss:GetObject\"\n" +
                "            ], \n" +
                "            \"Resource\": [\n" +
                "                \"acs:oss:*:*:"+bucketName+"/courseware/*\", \n" +
                "                \"acs:oss:*:*:"+bucketName+"/assignmentAnnex/*\", \n" +
                "                \"acs:oss:*:*:"+bucketName+"/assignmentSubmission/*\" \n" +
                "            ], \n" +
                "            \"Effect\": \"Allow\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        return teacherReadOnlyPolicy;
    }


    public static String getTeacherPutOnlyPolicy(Integer teacherId){
        String teacherPutOnlyPolicy = "{\n" +
                "    \"Version\": \"1\", \n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Action\": [\n" +
                "                \"oss:PutObject\"\n" +
                "            ], \n" +
                "            \"Resource\": [\n" +
                "                \"acs:oss:*:*:"+bucketName+"/courseware/teacher_"+teacherId+"/*\", \n" +
                "                \"acs:oss:*:*:"+bucketName+"/assignmentAnnex/teacher_"+teacherId+"/*\" \n" +
                "            ], \n" +
                "            \"Effect\": \"Allow\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        return teacherPutOnlyPolicy;
    }
}
