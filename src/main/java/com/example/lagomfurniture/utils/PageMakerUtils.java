package com.example.lagomfurniture.utils;

public class PageMakerUtils {
    private int totalcount;// 전체 게시물 개수
    private int pagenum;// 현재 페이지 번호
    private int contentnum = 10;// 한 페이지에 보일 게시물의 개수
    private int startPage = 1;// 현재 페이지 블록의 "시작" 페이지
    private int endPage = 10;// 현재 페이지 블록의 "마지막" 페이지
    private boolean prev = false;// "이전" 페이지 화살표
    private boolean next;// "다음" 페이지 화살표
    private int currentblock;// 현재 페이지 블록
    private int lastblock;// "마지막" 페이지 블록
    private int firstPage = 1;// 전체 게시물의 첫페이지
    private int lastPage;// 전체 게시물의 마지막 페이지
    private int currentPage; // 현재 페이지


    public void prevnext(int pagenum) {
        // 현재 페이지가 첫번째 페이지 블록 안에 있으면,
        // 이전 페이지로 가는 화살표는 보이지 않는다. 다음페이지 화살표는 보인다.
        //
        if (pagenum > 0 && pagenum < 11) {
            setPrev(false);
            setNext(true);
            // 라스트 블록을 가져오고, 현재 블록과 비교한다.
        } else if (getLastblock() == getCurrentblock()) {
            setPrev(true);
            setNext(false);
        } else {
            setPrev(true);
            setNext(true);
        }
    }

    // 게시판에 몇 페이지 까지 표시해야 할지 정하는 메소드가 필요하다.
    // totalcount는 전체 게시물 개수를 의미한다. contentnum 은 한 페이지에 몇개의 게시물을 표시 하는지 의미한다.
    public int calcpage(int totalcount, int contentnum) { // 전체 페이지 수를 계산하는 함수
        // 전체 게시물이 125 개 이고, 한 페이지에 10 개의 게시물을 표시 할 것이라면
        // 125 / 10 = 12.5 가 된다. 나머지가 0보다 크기 때문에 1을 더해줘서 전체 13 페이지의 게시판을 가지게 된다.
        // 50 / 10 = 5 가 되고 나머지가 0보다 크지 않기 때문에 전체 5 페이지의 게시판을 가지게 된다.
        int totalpage = totalcount / contentnum;
        if (totalcount % contentnum > 0) {
            totalpage++;
        }
        return totalpage;
    }

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    public int getPagenum() {
        return pagenum;
    }

    public void setPagenum(int pagenum) {
        this.pagenum = pagenum;
    }

    public int getContentnum() {
        return contentnum;
    }

    public void setContentnum(int contentnum) {
        this.contentnum = contentnum;
    }

    public int getStartPage() {
        return startPage;
    }

    // 한페이지에 1,2,3,4,5 페이지 씩 5개의 페이지가 보인다.
    // 1~5 (1) , 6~10 (2) , 11~13 (3) 식으로 한 페이지의 페이지 개수마다 1 블록으로 끊는다.
    // 현재 페이지 블록 n*5-4 하면 그 페이지 블록의 첫 페이지 숫자가 나온다.
    public void setStartPage(int currentblock) {
        // 10 페이지로 할거면 10 - 9 로 바꿔주기
        this.startPage = (currentblock * 10) - 9;
    }

    public int getEndPage() {
        return endPage;
    }

    // 한페이지에서 나타내는 블럭이 5개일 경우 보통 첫페이지 + 4 를 하면 그 블록이 완성이 된다.
    // 그러나 한가지 예외가 있다. 해당 블록이 마지막 페이지 일 경우
    // 4를 더하면 안되고 끝 페이지를 보여줘야 한다.
    // 그것을 if - else 문으로 처리 해줘야 한다.
    public void setEndPage(int getlastblock, int getcurrentblock) {
        if (getlastblock == getcurrentblock) {
            // 마지막 페이지 블록번호, 현재 페이지 블록번호를 이용해서
            // 현재 페이지 블록이 마지막 블록이면 전체 페이지 개수를 엔드페이지에 저장한다.
            this.endPage = calcpage(getTotalcount(), getContentnum());
        } else {
            // 그게 아닌 경우 시작 페이지를 구하고 5페이지만 보여주기 때문에 + 4 를 해준다.
            this.endPage = getStartPage() + 9;
        }

    }

    public boolean isPrev() {
        return prev;
    }

    public void setPrev(boolean prev) {
        this.prev = prev;
    }

    public boolean isNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public int getCurrentblock() {
        return currentblock;
    }

    public void setCurrentblock(int pagenum) {
        // 페이지 번호를 통해서 구한다.
        // 현재 페이지 번호가 1 페이지 라면
        // 페이지 번호 / 페이지 그룹 안의 페이지 개수
        // 1page = 1/5 = 0.2 = 0 번 블록은 없다. 나머지가 0보다 크므로 +1 해준다.
        // 3page = 3/5 = 0.xx = 0 / 0+1 페이지 블록1
        // 8page = 8/5 = 1.6 + => 1+1  = > 페이지 블록 2
        this.currentblock = pagenum / 10; // 1페이지당 5블록
        if (pagenum % 10 > 0) {
            this.currentblock++;
            System.out.println(this.currentblock);  // 5페이지로 갔는데 현재 블록이 증가하지 않음.
        }
    }

    public int getLastblock() {
        return lastblock;
    }

    // 10 개씩 게시물을 가지고 오고 한 페이지당 5개의 페이지 개수가 출력이 된다.
    // 1 페이지 블록에 10개의 게시물 x 페이지 개수 5 이므로 50 개의 게시물을 가지고 있다.
    // 6 ~ 10 도 50 개이다. 전체 게시물의 개수를 50으로 나눠주게 되면
    // 나머지가 나오고 몫이 나오게 될 것이다.
    // 거기에 더하기를 하게 되면 마지막 페이지 블록을 알 수 있게 된다.
    public void setLastblock(int totalcount) {
        // 10, 5 => 10*5 => 50
        // 125 / 50 = 2.5 . 나머지가 0보다 크기 때문에 1을 더하면 3.
        // 페이지 블럭이 3까지 존재 한다.
        this.lastblock = totalcount / (10 * this.contentnum);
        if (totalcount % (10 * this.contentnum) > 0) {
            this.lastblock++;
        }
    }

    // 첫페이지로 이동
    public int getFirstPage() {

        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public int getLastPage() {
        // 전체 게시물 개수 와 한페이지에 보일 개시물 게수
        return lastPage = calcpage(getTotalcount(), getContentnum());
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
