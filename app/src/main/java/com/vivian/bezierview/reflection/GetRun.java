package com.vivian.bezierview.reflection;

public class GetRun {
    public GetRun(RunTest runTest) {
        this.runTest = runTest;
    }

    private RunTest runTest;

    public void getSth() {
        runTest.run();
    }

}
