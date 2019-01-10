
// Assignment 6 - ECSE202
// Karl Michel Koerich
// 260870321
// Fall 2018


#include "stdlib.h"
#include "database.h"
#include "string.h"

//BTree Node
struct StudentRecord
{
    char first[64]; //Student first name
    char last[64]; //Student last name
    int ID; //Student ID
    int marks; //Student mark
    struct StudentRecord *left; //left node
    struct StudentRecord *right; //right node
};

//Add Node to BTree
struct StudentRecord *addNode(struct StudentRecord *root, struct StudentRecord data, int type) //LN=0 or LI=1 to facilitate
{
    //Parameter to stop
    if(root == NULL)
    {
        root = (struct StudentRecord*) malloc(sizeof(struct StudentRecord)+1); //Allocate memory for new node
        
        //Assign values and data to new node
        strcpy(root->first, data.first);
        strcpy(root->last, data.last);
        root -> ID = data.ID;
        root -> marks = data.marks;
        root -> left = NULL;
        root -> right = NULL;
        
    }
    else //Find empty space where node will be, try L-C-R
    {
        if (type == 0) //type == 0 means we are dealing with rootName
        {
            if (strcasecmp (data.last, root -> last) < 0) //Compare alphabetical order
            {
                root->left = addNode(root->left, data, type);
            }
            else
            {
                root->right = addNode(root->right, data, type);
            }
        }
        if(type == 1) //type == 1 means we are dealing with rootId
        {
            if (root->ID > data.ID) //Compare higher id
            {
                root->left = addNode(root->left, data, type);
            }
            else
            {
                root->right = addNode(root->right, data, type);
            }
        }
    }
    
    return root; //returns new updated bTree
};

/* ============================================================ */

//Prints list inorder by Last Name
void inorderLastName (struct StudentRecord *root)
{
    if(root->left != NULL) inorderLastName (root->left);
    printf("\n%-14s %-18s %-8d %-5d", root->first, root->last, root->ID, root->marks);
    if(root->right != NULL) inorderLastName (root->right);
}

//Prints list inorder by ID
void inorderID (struct StudentRecord *root)
{
    if(root->left != NULL) inorderID(root->left);
    printf("\n%-14s %-18s %-8d %-5d", root->first, root->last, root->ID, root->marks);
    if(root->right != NULL) inorderID (root->right);
}

//Looks for name inorder
int findLastName (struct StudentRecord *root, char *com, int q) //q counts how many found to return and print in case 0 found
{
    if(root->left != NULL) q = q + findLastName (root->left, com, q);
    if(strcasecmp(com, root->last) == 0)
    {
        printf("\nStudent Name: %s %s\nStudent Id: %d\nTotal Grade: %d\n\n", root->first, root->last, root->ID, root->marks);
        q = q+1;
    }
    if(root->right != NULL) q = q + findLastName (root->right, com, q);
    return q;
}

//Looks for ID inorder
int findID (struct StudentRecord *root, int id, int q) //q counts how many found to return and print in case 0 found
{
    if(root->left != NULL) q = q + findID (root->left, id, q);
    if(id == root->ID)
    {
        printf("\nStudent Name: %s %s\nStudent Id: %d\nTotal Grade: %d\n\n", root->first, root->last, root->ID, root->marks);
        q = q+1;
    }
    if(root->right != NULL) q = q + findID (root->right, id, q);
    return q;
}

/* ============================================================ */

void run(struct StudentRecord *rootName, struct StudentRecord *rootID)
{
    // User options
    char LN[] = "ln"; //List by name
    char LI[] = "li"; //List by id
    char FN[] = "fn"; //Look by name
    char FI[] = "fi"; //Look by id
    char HELP[] = "help"; //Instructions
    char WHAT[] = "?"; //Instructions
    char Q[] = "quit"; //Exits
    
    while (1) // while(true)
    {
        //Reading from user
        char com[32]; //comand from user
        printf("\n\nsdb: ");
        scanf("%s",com);
        
        // Cases for each of the user options
        // strcasecmp compared 2 strings case insensitive
        if (strcasecmp (com, LN) == 0)
        {
            printf("\nStudent Record Database sorted by Last Name\n");
            inorderLastName(rootName);
            
        } else if (strcasecmp (com, LI) == 0)
        {
            printf("\nStudent Record Database sorted by Student ID\n");
            inorderID(rootID);
            
        } else if (strcasecmp (com, FN) == 0)
        {
            char name[32];
            printf("\nEnter name to search: ");
            scanf("%s", name);
            
            int q = findLastName(rootName, name, 0); //q is the quantity of students found
            if (q == 0)
            {
                printf("\nThere is no student with that name.\n");
            }
        } else if (strcasecmp (com, FI) == 0)
        {
            int id;
            printf("\nEnter Student ID to search: ");
            scanf("%d", &id);
            
            int q = findID(rootName, id, 0); //q is the quantity of students found
            if (q == 0)
            {
                printf("\nThere is no student with that ID.\n");
            }
            
        } else if (strcasecmp (com, HELP) == 0 || strcasecmp (com, WHAT) == 0)
        {
            printf("\nLN List all the records in the database ordered by last name.\n");
            printf("LI List all the records in the database ordered by student ID.\n");
            printf("FN Prompts for a name and lists the record of the student with the corresponding name.\n");
            printf("FI Prompts for a name and lists the record of the student with the corresponding ID.\n");
            printf("HELP Prints this list.\n");
            printf("? Prints this list\n");
            printf("QUIT Exits the program.\n");
            
        } else if (strcasecmp (com, Q) == 0) break;
        else
        {
            printf("\nValid input, please.\n");
        }
    }
}

/* ============================================================ */

int main (int argc, char *argv[])
{
    printf("\nBuilding database...\n");
    FILE *NamesIDs;
    FILE *Marks;
    
    if ((NamesIDs = fopen(argv[1],"r")) == NULL) // Open names Id file
    {
        printf("\nCan't open %s\n",argv[1]);
        return -1;
    }
    if ((Marks = fopen(argv[2],"r")) == NULL) // Open marks file
    {
        printf("\nCan't open %s\n",argv[2]);
        return -2;
    }
    
    //Start 2Btrees
    struct StudentRecord *rootName;
    struct StudentRecord *rootID;
    
    struct StudentRecord stu;
    int numrecords=0;
    
    while (fscanf(NamesIDs,"%s%s%d", &(stu.first[0]), &(stu.last[0]), &(stu.ID)) != EOF) // Scan record into structure
    {
        fscanf(Marks,"%d",&(stu.marks)); // Scan marks into structure too
        numrecords++;
        
        rootName = addNode(rootName, stu, 0); // copy to B-Tree sorted by last name (LN = 0)
        if (rootName==NULL) //
        {
            printf("\nError creating name B-Tree, aborted.\n");
            return -3;
            
        }
        rootID = addNode(rootID, stu, 1); // copy to B-Tree sorted by ID (LI = 1)
        if (rootID==NULL)
        {
            printf("\nError creating ID B-Tree, aborted.\n");
            return -4;
        }
    }
    
    //Close file scans
    fclose(NamesIDs);
    fclose(Marks);
    
    printf("\nFinished...\n");
    
    //Run user loop
    run(rootName, rootID);
    
    printf("\nProgram terminated.\n");
}






