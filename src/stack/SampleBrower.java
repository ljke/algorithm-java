package stack;

public class SampleBrower {
    private int currentPage;
    private StackBasedLinkedList backStack;
    private StackBasedLinkedList forwardStack;

    public SampleBrower() {
        this.currentPage = 0;
        this.backStack = new StackBasedLinkedList();
        this.forwardStack = new StackBasedLinkedList();
    }

    //设置currentPage，如果出错，要将currentPage设置为0
    private void showUrl(String prefix, int url) {
        if (url > 0) {
            this.currentPage = url;
            System.out.println(prefix + ":" + url);
        } else {
            this.currentPage = 0;
            System.out.println(prefix + " error");
        }
    }

    //错误的处理：无论如何都会将currentPage入栈，虽然currentPage可能会变成0，但是push机制保证0不会入栈
    public void open(int url) {
        this.backStack.push(this.currentPage);
        this.forwardStack.clear();
        showUrl("Open", url);
    }

    public void goBack() {
        int frame = backStack.pop();
        this.forwardStack.push(this.currentPage);
        showUrl("Back", frame);
    }

    public void goForward() {
        int frame = forwardStack.pop();
        this.backStack.push(this.currentPage);
        showUrl("Forward", frame);
    }

    public void checkCurrentPage() {
        System.out.println("Current:" + currentPage);
    }

    public static void main(String[] args) {
        SampleBrower brower = new SampleBrower();
        brower.open(1);
        brower.open(0);
        brower.open(2);
        brower.open(3);
        brower.goBack();
        brower.goBack();
        brower.goBack();
        for (int i = 1; i < 4; i++) {
            brower.open(i);
        }
        brower.goBack();
        brower.goBack();
        brower.goForward();
        brower.checkCurrentPage();
        brower.goBack();
        brower.goBack();
        brower.backStack.printAll();
        brower.forwardStack.printAll();
    }

}
