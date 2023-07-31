package com.example.EvaluationManagementSystem.controller;

import com.example.EvaluationManagementSystem.entity.CreateBatchEntity;
import com.example.EvaluationManagementSystem.model.CreateBatchRequestModel;
import com.example.EvaluationManagementSystem.repository.CreateBatchRepository;
import com.example.EvaluationManagementSystem.service.CreateBatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/batch")
@RequiredArgsConstructor
public class CreateBatchController {
    private final CreateBatchService createBatchService;
    private final CreateBatchRepository batchRepository;
    @PostMapping("/create")
    public ResponseEntity<Object> booksCreate(@RequestBody CreateBatchRequestModel createBatchRequestModel){
        return createBatchService.createBatch(createBatchRequestModel);
    }
    @GetMapping("/allBatches")
    public ResponseEntity<Object> getAllBatches(){
        return createBatchService.getAllBatches();
    }
    @GetMapping("/allBatchName")
    public ResponseEntity<Object> getAllBatchName() {
        List<CreateBatchEntity> batchList = batchRepository.findAll();

        List<Map<String, Object>> batches = batchList.stream()
                .map(batch -> {
                    Map<String, Object> batchData = new HashMap<>();
                    batchData.put("id", batch.getId());
                    batchData.put("name", batch.getBatchName());
                    return batchData;
                })
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("TotalBatch", batches.size());
        response.put("Batches", batches);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{batchId}")//ok
    public ResponseEntity<Object>getBatchDetailsById(@PathVariable Long batchId){
            return createBatchService.getBatchByid(batchId);
    }
    @DeleteMapping("/delete/{batchId}")//okk
    public void deleteAdmin(@PathVariable Long batchId){
        createBatchService.deleteBatch(batchId);
    }

}
