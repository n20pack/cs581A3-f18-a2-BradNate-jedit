package org.gjt.sp.jedit.pluginmgr;


import java.util.List;
import org.gjt.sp.jedit.pluginmgr.Roster.Operation;

public class RosterOperations {
	private List<Operation> operations;

	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	public Operation getOperation(int i) {
		return operations.get(i);
	}

	public int getOperationCount() {
		return operations.size();
	}

	public boolean isEmpty() {
		return operations.isEmpty();
	}

	public void addOperation(Operation op) {
		for (Operation operation : operations) {
			if (operation.equals(op))
				return;
		}
		operations.add(op);
	}

	public void performOperationsInWorkThread(PluginManagerProgress progress) {
		for (Operation op : operations) {
			op.runInWorkThread(progress);
			progress.done();
			if (Thread.interrupted())
				return;
		}
	}
}