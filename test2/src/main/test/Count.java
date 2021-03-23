public class Count {

    private static boolean judge(int figure) {
        boolean flag = false;
        if (figure % 2 == 0 && figure % 3 == 0 && figure % 7 == 2) {
            flag = true;
        }
        return flag;
    }

    public static void main(String[] args) {
        int sum = 0;
        System.out.println("1-1000以内，能被2、3整除，且被7相除余数为2的数有这些：");
        for (int i = 1; i <= 1000; i++) {
            if (judge(i)) {
                System.out.print(i + "\t");
                sum += i;
            }
        }
        System.out.println("\n它们的和是：" + sum);
    }
}
