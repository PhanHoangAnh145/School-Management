package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Transcription;
import vn.edu.ptit.PhanHoangAnh.student_management.helper.ApiResponse;
import vn.edu.ptit.PhanHoangAnh.student_management.service.TranscriptionService;
import java.util.List;

@RestController
@RequestMapping("/api/transcription")
public class TranscriptionController {

    private TranscriptionService transcriptionService;

    @Autowired
    public TranscriptionController(TranscriptionService transcriptionService) {
        this.transcriptionService = transcriptionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Transcription>> findTranscriptionById(@PathVariable Long id) {
        Transcription transcription = this.transcriptionService.findTranscriptionById(id);
        return ApiResponse.success(transcription);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<Transcription>>> findAllTranscription() {
        List<Transcription> transcriptionList = this.transcriptionService.findAllTranscription();
        return ApiResponse.success(transcriptionList);
    }

    @PostMapping("/{studentId}")
    public ResponseEntity<ApiResponse<Transcription>> saveTranscription(@PathVariable Long studentId, @RequestBody Transcription transcription) {
        Transcription transcriptionSave = this.transcriptionService.saveTranscription(studentId, transcription);
        return ApiResponse.created(transcriptionSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Transcription>> updateTranscriptionById(@PathVariable Long id, @RequestBody Transcription transcription) {
        Transcription transcriptionUpdate = this.transcriptionService.updateTranscriptionById(id, transcription);
        return ApiResponse.success(transcriptionUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTranscriptionById(@PathVariable Long id) {
        this.transcriptionService.deleteTranscriptionById(id);
        return ApiResponse.success("delete success...");
    }
}