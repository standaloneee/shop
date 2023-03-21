package ru.shop.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shop.entity.Feedback;
import ru.shop.entity.Product;
import ru.shop.repository.FeedbackRepository;
import ru.shop.utils.ByteSequenceGenerator;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;



    public Feedback setUUIDAndSaveFeedback(Feedback feedback, String productId, double grade){
        feedback.setId(
                UUID.nameUUIDFromBytes(
                ByteSequenceGenerator.StringsToByteArray(feedback.getDescription(),
                        feedback.getSubject(),
                        productId,
                        String.valueOf(grade),
                        feedback.getUser().getUserName()
                        )
                )
        );
        return feedbackRepository.save(feedback);
    }

}
