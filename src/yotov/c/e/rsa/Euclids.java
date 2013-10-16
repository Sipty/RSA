package yotov.c.e.rsa;

// Euclid's
int euclids(int a, int b) {
    int temp=0;
    
    // make sure a > b
    if(a<b) {
        temp = a;
        a = b;
        b = temp;
    }
    
    // loop Euclid's until done
    while(true)
    {
        temp = a%b;
        a = b;
        b = temp;
        
        if(b==0)
            return a;
    }
}