package readerwriter;


public class RWLock {
    int numOfReaders = 0;

    int numOfWritersWaiting = 0;
    boolean writerIsActive = false;


    public synchronized void acquireRead(){
        numOfReaders++;
        if(writerIsActive || numOfWritersWaiting != 0 ){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public synchronized void acquireWrite(){
        if (numOfReaders != 0 || writerIsActive){
            try {
                numOfWritersWaiting++;
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        numOfWritersWaiting--;
        writerIsActive = true;

    }
    public synchronized void releaseRead(){
        numOfReaders--;
        notify();
    }

    public synchronized void releaseWrite(){
        writerIsActive = false;
        notify();

    }
}

/*package readerwriter;

import java.util.concurrent.locks.ReentrantReadWriteLock;
public class RWLock {

    ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

    public RWLock(){

    }

    public void acquireRead(){
        lock.readLock().lock();

    }

    public void acquireWrite(){
        lock.writeLock().lock();

    }

    public void releaseRead(){
        lock.readLock().unlock();

    }

    public void releaseWrite(){
        lock.writeLock().unlock();

    }

}*/

