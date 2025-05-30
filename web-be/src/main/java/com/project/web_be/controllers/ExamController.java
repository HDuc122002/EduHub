package com.project.web_be.controllers;


import com.project.web_be.dtos.ExamDTO;
import com.project.web_be.entities.Exam;
import com.project.web_be.responses.ExamResponse;
import com.project.web_be.services.Impl.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/exams")
@RequiredArgsConstructor
public class ExamController {
    private final ExamService examService;

    @PostMapping("add")
    public ResponseEntity<?> addExam(@RequestBody ExamDTO examDTO){
        try {
            Exam exam = examService.addExam(examDTO);
            return ResponseEntity.ok(exam);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getExamById(@PathVariable("id") long id){
        try {
            Exam exam = examService.getExamById(id);
//            return ResponseEntity.ok(ExamResponse.fromExam(exam));
            return ResponseEntity.ok(exam);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/teacher/{id}")
    public ResponseEntity<?> getAllExamsByTeacher(@PathVariable Long id){
        try {
            List<Exam> exams = examService.getAllExamsByTeacherId(id);
            List<ExamResponse> examResponses = exams.stream()
                    .map(ExamResponse::fromExam)
                    .toList();
            return ResponseEntity.ok(examResponses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateExam(@RequestBody ExamDTO examDTO, @PathVariable long id){
        try {
            Exam exam = examService.updateExam(examDTO, id);
            return ResponseEntity.ok(exam);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExam(@PathVariable long id){
        examService.deleteExam(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Delete exam successfully");
        return ResponseEntity.ok(response);
    }
}
