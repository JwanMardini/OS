/*package readerwriter;


public class RWLock {
    int numOfReaders = 0;

    int numOfWritersWaiting = 0;
    boolean writerIsActive = false;


    public synchronized void acquireRead(){

        while(writerIsActive || numOfWritersWaiting != 0 ){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        numOfReaders++;

    }

    public synchronized void acquireWrite(){
        numOfWritersWaiting++;
        while (numOfReaders != 0 || writerIsActive){
            try {
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
        notifyAll();
    }

    public synchronized void releaseWrite(){
        writerIsActive = false;
        notifyAll();

    }
}*/

package readerwriter;

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

}

