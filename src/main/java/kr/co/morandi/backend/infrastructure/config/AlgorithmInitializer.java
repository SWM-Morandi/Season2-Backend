package kr.co.morandi.backend.infrastructure.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;

import kr.co.morandi.backend.domain.algorithm.model.Algorithm;
import kr.co.morandi.backend.infrastructure.persistence.algorithm.AlgorithmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AlgorithmInitializer {

    private final AlgorithmRepository algorithmRepository;
    private final ObjectMapper objectMapper;

    @Value("classpath:Algorithms.json")
    private Resource algorithmsResource;

    @PostConstruct
    @Transactional
    public void init() throws IOException {
        final List<Algorithm> initialAlgorithms = objectMapper.readValue(algorithmsResource.getInputStream(), new TypeReference<>() {});
        final List<Algorithm> uninitialized = collectUninitialized(initialAlgorithms);

        algorithmRepository.saveAll(uninitialized);
    }

    private List<Algorithm> collectUninitialized(List<Algorithm> initialAlgorithms) {
        final List<Algorithm> findAll = algorithmRepository.findAll();

        final Set<Integer> collect = findAll.stream()
                .map(Algorithm::getBojTagId)
                .collect(Collectors.toSet());

        return initialAlgorithms.stream()
                .filter(algorithm -> !collect.contains(algorithm.getBojTagId()))
                .toList();
    }

}
