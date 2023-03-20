package ru.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shop.entity.Tag;
import ru.shop.exception.TagNotFoundException;
import ru.shop.repository.TagRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public Tag findTagByName(String name){
        return tagRepository.findTagByName(name).orElseThrow(() -> new TagNotFoundException(name));
    }

//    @Transactional
    public Set<Tag> addTags(Set<Tag> tags){
        tags.forEach(item -> item.setId(UUID.nameUUIDFromBytes(item.getName().getBytes())));
//        for (var item: tags) {
//            System.out.println(item.getId() + " \n" + item.getName());
//        }
        return new HashSet<>(tagRepository.saveAll(tags));
    }

}
