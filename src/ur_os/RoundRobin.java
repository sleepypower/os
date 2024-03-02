/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os;

/**
 *
 * @author prestamour
 */
public class RoundRobin extends Scheduler {

    int quantum;
    int executedCyclesInBurst;

    RoundRobin(OS os) {
        super(os);
        quantum = 3;
        executedCyclesInBurst = 0;
    }

    RoundRobin(OS os, int q) {
        this(os);
        this.quantum = q;
    }

    @Override
    public void getNext(boolean cpuEmpty) {
        if (processes.isEmpty()) {
            return;
        }

        if (cpuEmpty) {
            os.interrupt(InterruptType.SCHEDULER_RQ_TO_CPU, processes.getFirst());
            executedCyclesInBurst = 1;

            if (this.os.cpu.p.getRemainingTimeInCurrentBurst() == 1) {
                processes.removeFirst();
            }

            return;
        }

        if (executedCyclesInBurst < quantum) {
            executedCyclesInBurst++;

            if (this.os.cpu.p.getRemainingTimeInCurrentBurst() == 1) {
                processes.removeFirst();
            }

            return;
        }

        // we could avoid the overhead of the context switch if there is only one
        // process
        processes.removeFirst();

        Process temp = null;
        if (!processes.isEmpty()) {
            temp = processes.getFirst();
        }

        os.interrupt(InterruptType.SCHEDULER_CPU_TO_RQ, temp);
        executedCyclesInBurst = 1;
    }

    @Override
    public void newProcess(boolean cpuEmpty) {
    } // Non-preemtive in this event

    @Override
    public void IOReturningProcess(boolean cpuEmpty) {
    } // Non-preemtive in this event

}
