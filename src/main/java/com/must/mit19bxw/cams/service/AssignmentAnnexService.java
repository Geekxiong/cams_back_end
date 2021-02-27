package com.must.mit19bxw.cams.service;

import com.must.mit19bxw.cams.entity.AssignmentAnnex;

public interface AssignmentAnnexService {
    AssignmentAnnex add(String name, String savePath, Integer assignmentId, Integer teacherId) throws Exception;

    void deleteById(Integer id, Integer teacherId) throws Exception;
}
