public class Arithmetic {
    int firstNumber;
    int secondNumber;

    public Arithmetic(int firstNumber, int secondNumber) {
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
    }

    public int sumOfNumbers(int firstNumber, int secondNumber){
        return firstNumber + secondNumber;
    }

    public int productOfNumbers(int firstNumber, int secondNumber){
        return firstNumber * secondNumber;
    }

    public int min(int firstNumber, int secondNumber){
        if(firstNumber < secondNumber){
            return firstNumber;
        }
        return secondNumber;
    }

    public int max(int firstNumber, int secondNumber){
        if(firstNumber > secondNumber){
            return firstNumber;
        }
        return secondNumber;
    }
}
