package com.inspection.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspection.dao.InspectionDAO;
import com.inspection.vo.InspectionVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

@WebServlet(name = "SaveInspectionServlet", urlPatterns = "/api/inspection/save")
public class SaveInspectionServlet extends HttpServlet {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");

        JsonNode body = MAPPER.readTree(req.getInputStream());
        InspectionVO vo = new InspectionVO();
        vo.setTemplateName(body.path("templateName").asText(""));
        vo.setCategory(body.path("category").asText(""));
        vo.setWorkerName(body.path("workerName").asText(""));

        JsonNode changedCells = body.path("changedCells");
        vo.setRawData(MAPPER.writeValueAsString(changedCells));
        vo.setStatus(isAbnormal(changedCells) ? "Abnormal" : "Completed");

        try {
            int id = InspectionDAO.getInstance().saveInspection(vo);
            resp.getWriter().write("{\"ok\":true,\"id\":" + id + ",\"status\":\"" + vo.getStatus() + "\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"ok\":false,\"message\":\"" + e.getMessage().replace("\"", "'") + "\"}");
        }
    }

    private boolean isAbnormal(JsonNode changedCells) {
        if (changedCells == null || !changedCells.isObject()) {
            return false;
        }
        Iterator<Map.Entry<String, JsonNode>> it = changedCells.fields();
        while (it.hasNext()) {
            Map.Entry<String, JsonNode> entry = it.next();
            String cellRef = entry.getKey();
            JsonNode valueNode = entry.getValue();

            if (cellRef.matches("^[B-D](?:[5-9]|[1-3][0-9])$") && valueNode.isNumber()) {
                double value = valueNode.asDouble();
                if (value < 0 || value > 200) {
                    return true;
                }
            }
        }
        return false;
    }
}
