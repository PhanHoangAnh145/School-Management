package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Transcription;
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
    public Transcription findTranscriptionById(@PathVariable int id) {
        return this.transcriptionService.findTranscriptionById(id);
    }

    @GetMapping()
    public List<Transcription> findAllTranscription() {
        return this.transcriptionService.findAllTranscription();
    }

    @PostMapping("/{studentId}")
    public Transcription saveTranscription(@PathVariable int studentId, @RequestBody Transcription transcription) {
        return this.transcriptionService.saveTranscription(studentId, transcription);
    }

    @PutMapping("/{id}")
    public Transcription updateTranscriptionById(@PathVariable int id, @RequestBody Transcription transcription) {
        return this.transcriptionService.updateTranscriptionById(id, transcription);
    }

    @DeleteMapping("/{id}")
    public void deleteTranscriptionById(@PathVariable int id) {
        this.transcriptionService.deleteTranscriptionById(id);
    }
}