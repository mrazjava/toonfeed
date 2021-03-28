package com.github.mrazjava.toonfeed;

public class ToonTrigger extends AbstractToonTrigger {

    private AbstractToonProperties props;
    
    
    public ToonTrigger(AbstractToonProperties toonProperties) {
        this.props = toonProperties;
    }
    
    @Override
    public int getFetchLimit() {
        return props.getFetchLimit();
    }

    @Override
    protected int getInitialDelayMs() {
        return props.getPullDelayMs();
    }

    @Override
    protected int getPullDelayMin() {
        return props.getPullDelayMin();
    }
}
