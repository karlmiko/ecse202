
// Assignment 5 - ECSE202
// Karl Michel Koerich
// 260870321
// Fall 2018

#include "dec2base.h"
#include "stdio.h"
#include "stdlib.h"

void revStr(char *str, int length)
{
    char* rev_str = (char*)(malloc(length*sizeof(char))); //local variable without fixed length
                                                                //will take reverse of str
    int i;
    for(i = 0; i < length; i++) // From last index to first
    {
        rev_str[length-1-i] = str[i]; // Substract 1 to account for the last index on str
    }
    for(int i = 0; i < length; i++) // Add rev_str values to str
    {
        str[i] = rev_str[i];
    }
}

void dec2base(int input, int base, char *str)
{
    int i = 0; //index to access positions on str
    int quo = 10; //quotient (random value to get inside loop)
    int inp = input; // starting inp is the user input
    int rem = 0; //remainder
    while (quo>0)
    {
        quo = inp/base;
        rem = inp%base;
        inp = quo; //new value for inp
        
        // to store value in str as char
        if (rem<10)
        {
            str[i] = rem + '0';
        }
        else
        {
            str[i] = 'A' + rem-10;
        }
        
        i++; //To save value to the next index and keep track of the length
    }
    
    int length = i; // At the end of the loop, i is the length
    revStr(str, length); //Call function to reverse values on str
    
    //print result
    printf("The base-%d form of %d is: ", base, input);
    for(int i = 0; i < length; i++)
    {
        printf("%c", str[i]);
    }
    printf("\n");
}

int main (int argc, char *argv[])
{
    int input, base;
    if (argc == 3 || argc == 2) //it can either take 1 or 2 inputs from user + argc (thats why 3 or 2)
    {
        sscanf(argv[1], "%d", &input); // Assign value of first input to the variable input
        // if nothing on input for base, the deafult is base = 2
        if (argc == 2)
        {
            base = 2; // Assign value of 2 to the base
        }
        else
        {
            sscanf(argv[2], "%d", &base); // Assign value to base in case there is an input
        }
        
        //Check for accpetable ranges
        if (!(0 <= input && input <= 2147483647))
        {
            printf("Error: number must be in the range of [0,2147483647]\n");
            return (0);
        }
        if (!(2 <= base && base <= 36))
        {
            printf("Error: base must be in the range of [2,36]\n");
            return (0);
        }
    }
    else
    {
        printf("Wrong number of arguments!\n");
        return (0);
    }
    
    char* str= (char*)(malloc(1*sizeof(char))); //Creates str with size 1 but expandable.
    dec2base(input, base, str);
}
