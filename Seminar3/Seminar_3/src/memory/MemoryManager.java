package memory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.Queue;

public class MemoryManager {

	private int myNumberOfPages;
	private int myPageSize; // In bytes
	private int myNumberOfFrames;
	private int[] myPageTable; // -1 if page is not in physical memory
	private byte[] myRAM; // physical memory RAM
	private RandomAccessFile myPageFile;
	private int myNextFreeFramePosition = 0;
	private int myNumberOfpageFaults = 0;
	private int myPageReplacementAlgorithm = 0;

	private Queue<Integer> fifoQue = new LinkedList<>();

	private LinkedList<Integer> lruQueue = new LinkedList<>();

	public MemoryManager(int numberOfPages, int pageSize, int numberOfFrames, String pageFile,
			int pageReplacementAlgorithm) {


		myNumberOfPages = numberOfPages;
		myPageSize = pageSize;
		myNumberOfFrames = numberOfFrames;
		myPageReplacementAlgorithm = pageReplacementAlgorithm;

		initPageTable();
		myRAM = new byte[myNumberOfFrames * myPageSize];

		try {

			myPageFile = new RandomAccessFile(pageFile, "r");

		} catch (FileNotFoundException ex) {
			System.out.println("Can't open page file: " + ex.getMessage());
		}
	}

	private void initPageTable() {
		myPageTable = new int[myNumberOfPages];
		for (int n = 0; n < myNumberOfPages; n++) {
			myPageTable[n] = -1;
		}
	}

	public byte readFromMemory(int logicalAddress) {
		int pageNumber = getPageNumber(logicalAddress);
		int offset = getPageOffset(logicalAddress);

		if (myPageTable[pageNumber] == -1) {
			pageFault(pageNumber);
		}

		int frame = myPageTable[pageNumber];
		int physicalAddress = frame * myPageSize + offset;
		byte data = myRAM[physicalAddress];

		//System.out.print("Virtual address: " + logicalAddress);
		//System.out.print(" Physical address: " + physicalAddress);
		//System.out.println(" Value: " + data);
		return data;
	}

	private int getPageNumber(int logicalAddress) {
		// int pageNum = logicalAddress >> 8;
		int pageNum = logicalAddress / myPageSize;
		return pageNum;
	}

	private int getPageOffset(int logicalAddress) {
		//int pageOffset = logicalAddress & 0xFF;
		int pageOffset = logicalAddress % myPageSize;
		return pageOffset;
	}

	private void pageFault(int pageNumber) {
		if (myPageReplacementAlgorithm == Seminar3.NO_PAGE_REPLACEMENT)
			handlePageFault(pageNumber);

		if (myPageReplacementAlgorithm == Seminar3.FIFO_PAGE_REPLACEMENT)
			handlePageFaultFIFO(pageNumber);

		if (myPageReplacementAlgorithm == Seminar3.LRU_PAGE_REPLACEMENT)
			handlePageFaultLRU(pageNumber);

		readFromPageFileToMemory(pageNumber);
	}

	private void readFromPageFileToMemory(int pageNumber) {
		try {
			int frame = myPageTable[pageNumber];
			myPageFile.seek(pageNumber * myPageSize);
			for (int b = 0; b < myPageSize; b++)
				myRAM[frame * myPageSize + b] = myPageFile.readByte();
		} catch (IOException ex) {

		}
	}

	public int getNumberOfPageFaults() {
		return myNumberOfpageFaults;
	}

	private void handlePageFault(int pageNumber) {
		// Implement by student in task one
		// This is the simple case where we assume same size of physical and logical
		// memory
		// nextFreeFramePosition is used to point to next free frame position
		myPageTable[pageNumber] = myNextFreeFramePosition;
		myNextFreeFramePosition++;
		myNumberOfpageFaults++;
	}

	private void handlePageFaultFIFO(int pageNumber) { // page number is in page table
		if (fifoQue.size() < myNumberOfFrames) {
			// There are free frames, so assign the next free frame to the page
			fifoQue.add(pageNumber);
			myPageTable[pageNumber] = myNextFreeFramePosition;
			myNextFreeFramePosition++;
			myNumberOfpageFaults++;
		} else {
			// All frames are occupied, so we need to replace a page using FIFO
			int victimPage = fifoQue.poll(); // Dequeue the oldest page
			fifoQue.add(pageNumber); // Enqueue the new page (since it's now the most recent)
			myPageTable[pageNumber] = myPageTable[victimPage]; // Assign the new page to the victim's frame
			myPageTable[victimPage] = -1; // Remove the victim page from memory
			myNumberOfpageFaults++;
		}
	}


	private void handlePageFaultLRU(int pageNumber) {
		if (lruQueue.size() < myNumberOfFrames) {
			// There are free frames, so assign the next free frame to the page
			lruQueue.add(pageNumber);
			myPageTable[pageNumber] = myNextFreeFramePosition;
			myNextFreeFramePosition++;
			myNumberOfpageFaults++;
		} else {
			// All frames are occupied, so we need to replace a page using LRU
			int leastRecentlyUsedPage = lruQueue.removeLast(); // Remove the least recently used page
			lruQueue.addFirst(pageNumber); // Add it back as the most recently used
			myPageTable[pageNumber] = myPageTable[leastRecentlyUsedPage]; // Assign the new page to the freed frame
			myPageTable[leastRecentlyUsedPage] = -1; // Remove the least recently used page from memory
			myNumberOfpageFaults++;
		}

	}

}
