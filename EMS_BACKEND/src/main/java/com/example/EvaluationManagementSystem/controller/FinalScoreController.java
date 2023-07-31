package com.example.EvaluationManagementSystem.controller;


import com.example.EvaluationManagementSystem.model.FinalScoreRequestModel;
import com.example.EvaluationManagementSystem.service.FinalScoreService;
import com.example.EvaluationManagementSystem.service.impl.FinalScoreServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/final")
@RequiredArgsConstructor
public class FinalScoreController {
   private final FinalScoreService finalScoreService;
   @PostMapping("/create/{batchId}")
   public ResponseEntity<List<Object>> createFinalMarksForWholeBatch(@PathVariable Long batchId, @RequestBody FinalScoreRequestModel finalScoreRequestModel){
      return finalScoreService.finalScoreFullBatch(batchId,finalScoreRequestModel);
   }
   @GetMapping("/marks/{batchId}")
   public ResponseEntity<List<Object>> showFinalMarksUnderBatch(@PathVariable Long batchId){
      return  finalScoreService.viewAllMarksUnderBatchId(batchId);
   }

}
