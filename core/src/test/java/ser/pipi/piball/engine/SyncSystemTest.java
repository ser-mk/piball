package ser.pipi.piball.engine;

import org.junit.Test;

import java.awt.SystemTray;

import static org.junit.Assert.*;

/**
 * Created by ser on 18.04.18.
 */
public class SyncSystemTest {

    class State{
        int i = 0;
    }


    class Worker{
        public State state;
        public Worker(State state){
            this.state = state;
        }

        void update(State state){
            this.state = state;
        }
    }

    class Worker2{
        public Wrap wrap;
        public Worker2(Wrap wrap){
            this.wrap = wrap;
        }

        void update(State state){
            this.wrap.state = state;
        }
    }

    class Boss{
        public final Wrap wrap;

        public Boss(Wrap wrap) {
            this.wrap = wrap;
        }
    }

    class Wrap{
        State state;
    }

    @Test
    public void recieve() throws Exception {
        final Wrap wrap = new Wrap();
        wrap.state = new State();
        final State state = new State();

        Worker worker = new Worker(wrap.state);

        Boss boss = new Boss(wrap);

        final State modifyState = new State();
        modifyState.i = 1;

        worker.update(modifyState);

        Worker2 worker2 = new Worker2(wrap);
        worker2.update(modifyState);

        System.out.println(state.i);
        System.out.println(worker2.wrap.state.i);
        System.out.println(boss.wrap.state.i);
    }

    @Test
    public void finalTest() throws Exception {

        String test = "2.2";

        long l = Long.valueOf(test);
        System.out.println(l);
    }
}