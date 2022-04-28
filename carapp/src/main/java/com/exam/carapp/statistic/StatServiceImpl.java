package com.exam.carapp.statistic;

import com.exam.carapp.membership.Membership;
import com.exam.carapp.membership.MembershipRepository;
import com.exam.carapp.requests.byingRequests.BuyingRequestsRepository;
import com.exam.carapp.requests.byingRequests.model.BuyingRequest;
import com.exam.carapp.requests.serviceRequests.model.ServiceRequest;
import com.exam.carapp.requests.serviceRequests.repository.ServiceRequestsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatServiceImpl implements StatService {


    private final ServiceRequestsRepository serviceRequestsRepository;
    private final BuyingRequestsRepository buyingRequestsRepository;
    private final MembershipRepository memRepository;

    public StatServiceImpl(ServiceRequestsRepository serviceRequestsRepository, BuyingRequestsRepository buyingRequestsRepository, MembershipRepository memRepository) {
        this.serviceRequestsRepository = serviceRequestsRepository;
        this.buyingRequestsRepository = buyingRequestsRepository;
        this.memRepository = memRepository;
    }

    @Override
    public Statistic getData() {
        StatInfo buy = getBuyStat();
        StatInfo service = getServeStat();
        StatInfo total = buy;
        total.setProfit(buy.getProfit() + service.getProfit());
        total.setDone(buy.getDone() + service.getDone());
        total.setRejected(buy.getRejected() + service.getRejected());
        total.setCancelled(buy.getCancelled() + service.getCancelled());
        List<Membership> mems = memRepository.findAll();
        Integer memCount = mems.size();
        return new Statistic(buy, total, service, memCount);
    }

    private StatInfo getBuyStat() {
        List<BuyingRequest> list = buyingRequestsRepository.findAll();
        StatInfo stat = new StatInfo(0,0,0,0,0);
        for(BuyingRequest req: list) {
            if (req.getStatus().equals("DONE")) {
                stat.setDone(stat.getDone() + 1);
                stat.setProfit(stat.getProfit() + req.getPrice());
                stat.setTotal(stat.getTotal() + 1);
            } else if (req.getStatus().equals("REJECTED")) {
                stat.setRejected(stat.getRejected() + 1);
                stat.setTotal(stat.getTotal() + 1);
            } else if (req.getStatus().equals("CANCELLED")) {
                stat.setCancelled(stat.getCancelled() + 1);
                stat.setTotal(stat.getTotal() + 1);
            }
        }
        return stat;
    }

    private StatInfo getServeStat() {
        List<ServiceRequest> list = serviceRequestsRepository.findAll();
        StatInfo stat = new StatInfo(0,0,0,0,0);
        for(ServiceRequest req: list) {
            if (req.getStatus().equals("DONE")) {
                stat.setDone(stat.getDone() + 1);
                stat.setProfit(stat.getProfit() + req.getPrice());
                stat.setTotal(stat.getTotal() + 1);
            } else if (req.getStatus().equals("REJECTED")) {
                stat.setRejected(stat.getRejected() + 1);
                stat.setTotal(stat.getTotal() + 1);
            } else if (req.getStatus().equals("CANCELLED")) {
                stat.setCancelled(stat.getCancelled() + 1);
                stat.setTotal(stat.getTotal() + 1);
            }
        }
        return stat;
    }

}
