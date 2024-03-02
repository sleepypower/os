/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ur_os;

import java.io.Console;

/**
 *
 * @author super
 */
public class Process implements Comparable {
    public static final int NUM_CPU_CYCLES = 3;
    public static final int MAX_CPU_CYCLES = 10;
    public static final int MAX_IO_CYCLES = 10;
    int pid;
    int time_init;
    int time_finished;
    ProcessBurstList pbl;
    ProcessState state;

    public Process() {
        pid = -1;
        time_init = 0;
        time_finished = -1;
        pbl = new ProcessBurstList();
        pbl.generateRandomBursts(NUM_CPU_CYCLES, MAX_CPU_CYCLES, MAX_IO_CYCLES);
        // pbl.generateSimpleBursts(); // Generates process with 3 bursts (CPU, IO, CPU)
        // with 6 cycles each
        state = ProcessState.NEW;
    }

    public Process(int pid, int time_init, int cpucycles1, int iocycles, int cpucycles2) {
        this();
        this.pid = pid;
        this.time_init = time_init;
        pbl = new ProcessBurstList();
        pbl.generateControlledBursts(cpucycles1, iocycles, cpucycles2);
        state = ProcessState.NEW;

    }

    public Process(int pid, int time_init) {
        this();
        this.pid = pid;
        this.time_init = time_init;
    }

    public Process(Process p) {
        this.pid = p.pid;
        this.time_init = p.time_init;
        this.pbl = new ProcessBurstList(p.getPBL());
    }

    public boolean advanceBurst() {
        return pbl.advanceBurst();
    }

    public boolean isFinished() {
        return pbl.isFinished();
    }

    public void setTime_finished(int time_finished) {
        this.time_finished = time_finished;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getTime_init() {
        return time_init;
    }

    public void setTime_init(int time_init) {
        this.time_init = time_init;
    }

    public ProcessBurstList getPBL() {
        return pbl;
    }

    public ProcessState getState() {
        return state;
    }

    public void setState(ProcessState state) {
        this.state = state;
    }

    public int getRemainingTimeInCurrentBurst() {
        return pbl.getRemainingTimeInCurrentBurst();
    }

    public boolean isCurrentBurstCPU() {
        return pbl.isCurrentBurstCPU();
    }

    public String toString() {
        return "PID: " + pid + " t: " + time_init + " " + pbl;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Process) {
            Process p = (Process) o;
            return this.getPid() - p.getPid();
        }

        return -1;
    }

    @Override
    public boolean equals(Object o) {

        if (o instanceof Process) {
            Process p = (Process) o;
            return this.getPid() == p.getPid();
        }

        return false;

    }

}
