package ru.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shop.entity.Tag;
import ru.shop.exception.TagNotFoundException;
import ru.shop.repository.TagRepository;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public Tag findTagByName(String name){
        return tagRepository.findTagByName(name).orElseThrow(() -> new TagNotFoundException(name));
    }

    public void addTags(Set<Tag> tags){
        tags.forEach(item -> item.setId(UUID.nameUUIDFromBytes(item.getName().getBytes())));
        tagRepository.saveAll(tags);
    }

}
