package ru.shop.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shop.entity.Feedback;
import ru.shop.repository.FeedbackRepository;
import ru.shop.utils.ByteSequenceGenerator;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;



    public Feedback saveFeedback(Feedback feedback, String productId){
        feedback.setId(
                UUID.nameUUIDFromBytes(
                ByteSequenceGenerator.StringsToByteArray(feedback.getDescription(),
                        feedback.getSubject(),
                        productId,
                        feedback.getUser().getUserName()
                        )
                )
        );
        System.out.println(feedback.getId());
        return feedbackRepository.save(feedback);
//        return feedback;
    }

}
