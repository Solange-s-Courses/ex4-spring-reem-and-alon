package com.example.ex4.service;

import com.example.ex4.entity.Review;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ReviewLongPollingService {
    private final Map<Long, List<DeferredResult<List<Review>>>> waitingClients = new ConcurrentHashMap<>();

    public void addWaiter(Long serviceId, DeferredResult<List<Review>> deferred) {
        waitingClients.computeIfAbsent(serviceId, k -> new CopyOnWriteArrayList<>()).add(deferred);

        deferred.onCompletion(() -> {
            List<DeferredResult<List<Review>>> list = waitingClients.get(serviceId);
            if (list != null) list.remove(deferred);
        });
    }

    public void notifyClients(Long serviceId, List<Review> newReviews) {
        List<DeferredResult<List<Review>>> list = waitingClients.get(serviceId);
        if (list != null && !list.isEmpty()) {
            for (DeferredResult<List<Review>> deferred : list) {
                deferred.setResult(newReviews);
            }
            list.clear();
        }
    }
}
