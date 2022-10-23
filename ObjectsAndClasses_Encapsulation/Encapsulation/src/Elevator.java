public class Elevator {
    private int currentFloor = 1;
    private int minFloor;
    private int maxFloor;

    public Elevator(int minFloor, int maxFloor) {
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void moveDown(){
        currentFloor--;
    }
    public void moveUp(){
        currentFloor++;
    }
    public void move(int floor){
        if(floor <= maxFloor && floor > currentFloor){
            while (currentFloor != floor){
                moveUp();
                System.out.println(currentFloor);
            }
        }else if(floor >= minFloor && floor < currentFloor){
            while (currentFloor != floor){
                moveDown();
                System.out.println(currentFloor);
            }
        }else if (floor == currentFloor){

        }else {
            System.out.println("Ошибка: Этаж не существует!!!");
        }
    }
}
