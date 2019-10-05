package com.example.lagomfurniture.service;

import com.example.lagomfurniture.model.Product;
import com.example.lagomfurniture.model.Review;
import com.example.lagomfurniture.utils.PageMakerUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PageMakerService {

    public PageMakerUtils generatePageMaker(int pageNum, int contentNum, JpaRepository<Review, Long> repository) {
        int totalCount = (int) repository.count();
        PageMakerUtils pageMakerUtils = pageMakerSettings(totalCount, pageNum, contentNum, 10, 11, 10);
        return pageMakerUtils;
    }

    public PageMakerUtils generateProductPageMaker(int pageNum, int contentNum, Page<Product> products) {
        int totalCount = (int) products.getTotalElements();
        PageMakerUtils pageMakerUtils = pageMakerSettings(totalCount, pageNum, contentNum, 5, 6, 5);
        return pageMakerUtils;
    }

    private PageMakerUtils pageMakerSettings(int totalCount, int pageNum, int contentNum, int blockNum, int pageLimit, int setPage) {
        PageMakerUtils pageMakerUtils = new PageMakerUtils();

        pageMakerUtils.setTotalcount(totalCount);   // 전체 게시글 수를 지정한다.
        pageMakerUtils.setPagenum(pageNum -1);      // 현재 페이지를 페이지 객체에 지정한다. -1 를 해야 쿼리에서 사용 할 수 있다.
        pageMakerUtils.setContentnum(contentNum);   // 한 페이지에 몇개씩 게시글을 보여줄지 지정한다.
        pageMakerUtils.setCurrentblock(pageNum, blockNum);     // 현재 페이지 블록이 몇 번인지 현재 페이지 번호를 통해서 지정한다. blockNum / 10기준 10, 5기준 5
        pageMakerUtils.setCurrentPage(pageNum);     // 마지막 페이지를 지정한다.
        pageMakerUtils.setLastblock(pageMakerUtils.getTotalcount(), blockNum); // 마지막 블록 번호를 전체 게시글 수를 통해서 지정한다. blockNum / 10기준 10, 5기준 5
        pageMakerUtils.prevnext(pageNum, pageLimit);           // 현재 페이지 번호로 화살표를 나타낼지 정한다. pagelimit / 10기준 11, 5기준 6
        pageMakerUtils.setStartPage(pageMakerUtils.getCurrentblock(), setPage); // 시작 페이지를 페이지 블록 번호로 지정한다. setPage / 10기준 10, 5기준 5
        pageMakerUtils.setEndPage(pageMakerUtils.getLastblock(), pageMakerUtils.getCurrentblock(),setPage);
        // 마지막 페이지를 마지막 페이지 블록과 현재 페이지 블록 번호로 정한다. setPage / 10기준 10 (-9), 5기준 5 (-1)
        pageMakerUtils.setLastPage();

        return pageMakerUtils;
    }



}
