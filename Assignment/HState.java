// Class file for the state of the nodes used by the OBDDH diagram.
//
// Is not an internal class because different states are possible without changing the
// node structure.
 
import java.util.*; // For testing

class HState
{
  // Implementing HState as a linked list so adding an internal class
  // Each block is itself a list of vertices, so need a vertex list
  class Block
  {
    // Using an internal class directly since it's always used with Block
    class BlockEntry
    {
      private Vertex data;
      private BlockEntry next;

      public BlockEntry( Vertex v )
      {
        if ( v == null )
          throw new IllegalArgumentException("BlockEntry must be formed from a non-null vertex.");
        data = v;
        next = null;
      }
      public BlockEntry( BlockEntry b )
      {
        data = b.data;
        next = null;
      }
      public Vertex getData()
      {
        return data;
      }
    }

    BlockEntry list;
    BlockEntry tail;
    Block next;
    boolean marked;    // Set to true when the block is connected to the source
    boolean targetted; // Set to true when the block is connected to the target
    int count;

    // The main constructor
    // Creates a new block from the given vertex
    public Block( Vertex v )
    {
      list = new BlockEntry( v ); // Letting the entry check on input
      tail = list;
      marked = v.isSource();
      targetted = v.isTarget(); 
      next = null;
      count = 0;
    }

    // The copy constructor. Used for positive child nodes
    public Block( Block b )
    {
      BlockEntry temp;
      tail = null;
      for ( BlockEntry bl = b.list; bl != null; bl = bl.next )
      {
        temp = new BlockEntry( bl );
        if ( tail == null )
          list = temp;
        else
          tail.next = temp;
        tail = temp;
      }
      marked = b.marked;
      targetted = b.targetted;
      next = null;
      count = b.count;
    }

    // Return the first vertex in the block
    public Vertex getFirst()
    {
      if ( list == null )
      {
        throw new IllegalStateException("Trying to look at the first vertex in a null block.");
      }

      return list.getData();
    }

    // Returns true if the block contains the vertex
    public boolean hasVertex( Vertex v )
    {
      String vname = v.getName();
      int comp = 1;
      boolean found = false;
      boolean exit = false;

      if ( v == null )
      {
        throw new IllegalArgumentException("Can't have null vertex argument to hasVertex.");
      }

      for ( BlockEntry b = list; (comp>0) && (b != null); b = b.next )
      {
        comp = vname.compareTo( b.data.getName() );
        // If comp < 0 we keep looking
        if ( comp == 0 )
        {
          found = true;
        }
      }
      return found;
    }

    // Checks for block equality
    // This would be far more efficient if the block lists were sorted. Too much work at the moment
    public boolean isEqual( Block b )
    {
      boolean equal = true;
      boolean sub;
      String nm;

      if ( b.count != count )
      {
        equal = false;
      }
      for ( BlockEntry al = list; equal && (al != null); al = al.next )
      {
        nm = al.data.getName();
        sub = false;
        for ( BlockEntry bl = b.list; !sub && (bl != null); bl = bl.next )
        {
          if ( nm.equals( bl.data.getName() ) )
          {
            sub = true;
          }
        }
        if ( !sub )
          equal = false;
      }
      return equal;
    }

    // Delete the vertex from the block
    // Returns true if we've deleted the last vertex in this block
    public boolean delVertex( Vertex v )
    {
      String vname = v.getName();
      BlockEntry prev = null;
      boolean done = false;
      int comp = 0;
      for ( BlockEntry b = list; !done && (b != null); b = b.next )
      {
        comp = vname.compareTo( b.data.getName() );
        // If comp < 0 we keep looking
        if ( comp < 0 ) // v > b -> stop looking 
        {
          done = true;
        }
        else if ( comp == 0 )
        {
          // Delete the vertex
          if ( prev == null )
            list = b.next;
          else
            prev.next = b.next;
          done = true;
          count--;
        }
        else
          prev = b;
      }
      return (list == null);
    }

    // Uses insertion sort
    public boolean addVertex( Vertex v )
    {
      String vname = v.getName();
      boolean found = false;
      int comp;
      BlockEntry last = null;
      BlockEntry temp = new BlockEntry( v );
      for ( BlockEntry b = list; !found && (b != null); b = b.next )
      {
        comp = vname.compareTo( b.data.getName() );
        // If comp > 0 we keep looking
        if ( comp < 0 ) // v > b -> insert here
        {
          temp.next = b;
          if ( last == null )
          {
            list = temp;
          }
          else
          {
             last.next = temp;
          }
          found = true;
          count++;
        }
        else if ( comp == 0 )
        {
          found = true;
        }
        else
          last = b;
      }

      // Need to see if the loop ended because it was found, because it was inserted, or because the list ended
      // The only way to reach the end of the list if all comps returned negative.
      if ( !found )
      {
        temp.next = null;	// Inserting at end of list
        if ( last == null )
          list = temp;
        else
          last.next = temp;
        tail = temp;            // Since we're inserting at the end, this is the new tail
        count++;
      }
      // If we have added the target vertex, the block becomes targetted. We never add the source vertex
      targetted = v.isTarget();

 
      return found;
    }

    // Merge another block into this one
    // Comparable to the merge from merge sort
    public void merge( Block b )
    {
      BlockEntry al = list;
      BlockEntry bl = b.list;
      BlockEntry temp_head = null;
      BlockEntry temp_tail = null;
      String vname = null;
      int comp = 0;
      count = 0;
      
      while ( (al!=null) && (bl!=null) )
      {
        vname = al.data.getName();
        comp = vname.compareTo( bl.data.getName() );
        if ( comp > 0 ) // v > bl -> choose bl 
        {
          if ( temp_head == null ) // first element
          {
            temp_head = bl;
            temp_tail = bl;
            bl = bl.next;
          }
          else
          {
            temp_tail.next = bl;
            temp_tail = bl;
            bl = bl.next;
          }
          count++;
        }
        else if ( comp < 0 )
        {
          if ( temp_head == null ) // first element
          {
            temp_head = al;
            temp_tail = al;
            al = al.next;
          }
          else
          {
            temp_tail.next = al;
            temp_tail = al;
            al = al.next;
          }
          count++;
        }
        else
        {
          throw new IllegalStateException("One vertex found in two seperate blocks.");
        }
      }
      // Add the remainder of the other list
      // Assuming that neither block starts of null, so the temp list is non-empty
      while ( al != null )
      {
        temp_tail.next = al;
        temp_tail = al;
        al = al.next;
      }
      while ( bl != null )
      {
        temp_tail.next = bl;
        temp_tail = bl;
        bl = bl.next;
      }

      temp_tail.next = null;

      // Move merged list into this block
      list = temp_head;
      tail = temp_tail;

      if ( b.marked )
        marked = true;
      if ( b.targetted )
        targetted = true;
    }

    public void display()
    {
      for (BlockEntry b = list; b != null; b = b.next )
      {
        System.out.print( b.data.getName() );
        if ( b.next != null )
        {
          System.out.print(", ");
        }
      }
    }
  } // end of Block internal class

  // Start of HState class proper
  // ****************************
  private Block head;  // List of blocks that store vertices being considered
  private Block tail;
  private static boolean delFrom = false;
  private static boolean delTo = false;
  private static boolean addTo = true;
  boolean success;    // True if the state represents a successful connection
  boolean failure;     // True if the state represents a failed connection

  // The default constructor creates the state of the root node of the OBDDH diagram
  // It should never be used apart from this, since all other states result from the modification
  // of the parent state
  public HState( Vertex source ) 
  {
    head = new Block( source );
    tail = head;
    success = head.marked && head.targetted;
    failure = false;
  }

  // Set and reset mutators for the three booleans
  public static void setDelFrom()
  {
    delFrom = true;
  }
  public static void resetDelFrom()
  {
    delFrom = false;
  }
  public static void setDelTo()
  {
    delTo = true;
  }
  public static void resetDelTo()
  {
    delTo = false;
  }
  public static void setAddTo()
  {
    addTo = true;
  }
  public static void resetAddTo()
  {
    addTo = false;
  }

  // The copy constructor is used for the positive child.
  public HState( HState s )
  {
    Block temp;
    tail = null;
    for ( Block st = s.head; st != null; st = st.next )
    {
      temp = new Block( st );
      if ( tail == null )
        head = temp;
      else
        tail.next = temp;
      tail = temp;
    }
    // In general we can assume that both success and failed are false, since both success and failure
    // nodes are terminal (which means no child nodes are created from them). Since I'm not 100% sure that
    // this will always be the case I'm copying the values instead.
    success = s.success;
    failure = s.failure;
  }

  // Returns true if the given state is equal (isomorphic) to the current state 
  // Two states are equal if they contain the same vertices in the same blocks.
  // Because blocks and vertices in blocks are ordered, comparison is easy
  public boolean isEqual( HState s )
  {
    Block stail = s.tail;
    Block h = head;
    Block t = tail;
    boolean ret = true;
    
    for ( Block shead = s.head; ret && (shead != null) && (h != null); shead = shead.next )
    {
      if ( !shead.isEqual( h ) )  // Compare the entire block
        ret = false;              // Once one comparison is false, we can stop
      h = h.next;
    }
    return ret;
  }

  // Update the HState for an edge being available.
  // This is called a contraction. In essence, you can remove the edge from the graph and merge its two endpoints since
  // we now know that they are connected. In practice, it means making sure that both endpoints of the edge end up in the
  // same Block.
  //
  // What this means is:
  // * if the to vertex is new, add it to the block that contains the from vertex
  // * if not, then find what block the to vertex is in and merge that block with the first one
  public void contract( Edge e ) 
  {
    Block looking = null;
    Block hasFrom = null;
    Block prevFrom = null;
    Block hasTo = null;
    Block prevTo = null;
    boolean found = false;

    if ( e == null )
    {
      throw new IllegalArgumentException("Input to contract cannot be null.");
    }

    // Locate the "from" and "to" vertices
    for ( looking = head; !found && (looking != null); looking = looking.next )
    {
      if ( looking.hasVertex( e.getFrom() ) )
      {
        hasFrom = looking;
        if ( hasTo != null )
        {
          found = true;
        }
      }
      if ( looking.hasVertex( e.getTo() ) )
      {
        hasTo = looking;
        if ( hasFrom != null )
        {
          found = true;
        }
      }
      if ( hasFrom == null )
        prevFrom = looking;
      if ( hasTo == null )
        prevTo = looking;
    }
    // Sanity check
    if ( hasFrom == null )
      throw new IllegalArgumentException("From vertex of edge passed to contract does not exist in any block.");

    if ( hasTo == null ) // The "to" vertex is new - it doesn't appear in any current block. Add it to the From block
    {
      Vertex to = e.getTo();
      hasFrom.addVertex( to );
    }
    else         // We need to find the block containing the "to" vertex
    {
      // Sanity check
      if ( hasTo == null )
        throw new IllegalArgumentException("To vertex of edge passed to contract does not exist in any block but addTo is false.");

      // Now we check on whether the blocks are actually the same one. If so, we don't need to do anthing. If not, we merge them
      if ( hasFrom != hasTo ) // This is a pointer comparison, not a deep equality check in order to save time
      {
        // Remove hasTo from the list of blocks
        if ( prevTo == null ) // It's the first block
        {
          head = hasTo.next;
          hasTo.next = null;
        }
        else if ( hasTo.next == null ) // last block
        {
          tail = prevTo;
          prevTo.next = null;
        }
        else
        {
          prevTo.next = hasTo.next;
          hasTo.next = null;
        }
        // Now merge it into hasFrom to complete the contraction
        hasFrom.merge( hasTo );
      }
    }
    // We need to check whether the resulting block is successful. This happens if it's connected to both the source and target
    success = hasFrom.marked && hasFrom.targetted;
/*
if ( success )
{
System.out.println("Node is successful.");
}
*/
  } // End of method contract

  // Go through the state, removing the vertex from the block that it is in.
  // Assuming it must be in at least one block.
  void check( Vertex v )
  {
    Block looking = null;
    Block hasFrom = null;
    Block prevFrom = null;

    if ( v == null )
    {
      throw new IllegalArgumentException("Input to check cannot be null.");
    }

    // Locate the vertex
    for ( looking = head; (hasFrom == null) && (looking != null); looking = looking.next )
    {
      if ( looking.hasVertex( v ) )
      {
        hasFrom = looking;
      }
      else
      {
        prevFrom = looking;
      }
    }

    if ( hasFrom == null )
    {
      throw new IllegalStateException("HState::check - the vertex being decided wasn't found in any block.");
    }

    // Delete the vertex
    if ( delFrom )
    {
      boolean del = false;
      // Remove the block from the list 
      if ( head == hasFrom )
      {
        head = hasFrom.next;
      }
      if ( tail == hasFrom )
      {
        tail = prevFrom;
      }
      if ( prevFrom != null )
      {
        prevFrom.next = hasFrom.next;
      }
      hasFrom.next = null;

      del = hasFrom.delVertex( v );

      // del is set to true if the block hasFrom has now become empty. If it's also marked or targetted, the node is a failure node
      if ( del )
      {
        failure = (hasFrom.marked || hasFrom.targetted);
/*
if ( failure )
{
System.out.println("Node is failed.");
}
*/
      
      }
      else // re-insert the block
      {
        Block ptr;
        Block prev = null;
        String vname = hasFrom.getFirst().getName();
        boolean found = false;
        int comp;

        if ( head == null )
        {
          head = hasFrom;
          tail = hasFrom;
        }
        else
       {
        ptr = head;
        while ( !found && (ptr != null) )
        {
          comp = vname.compareTo( ptr.getFirst().getName() );
          if ( comp < 0 )
          {
            if ( prev == null ) // insert at front 
            {
              hasFrom.next = head;
              head = hasFrom;
            }
            else
            {
              hasFrom.next = prev.next;
              prev.next = hasFrom;
            }
            found = true;
          }
          prev = ptr;
          ptr = ptr.next;
        }
        if ( !found ) // Insert at the end of the list
        {
          tail.next = hasFrom;
          tail = hasFrom;
        }
       }
      }
    }
  }



  // Modifies the state of the node to account for component failure.
  // When an edge fails, we delete it from the graph, leaving the endpoints seperated (although they may be connected by other
  // paths.
  // In terms of state modification, we don't do much other than adding a single block containing the new vertex if it wasn't
  // already present
  public void delete( Edge e ) 
  {
    Vertex v = e.getTo();
    // If the 'to' vertex doesn't yet exist, create the new block containing it and add it to the appropriate part of the list
    if ( addTo )
    {
      Block temp = new Block( v );
      if ( tail == null ) // This means the list is empty, which should never actually happen. I think.
      {
        head = temp;
        tail = temp;
      }
      else
      {
        Block ptr;
        Block prev = null;
        String vname = v.getName();
        boolean found = false;
        int comp;

        for ( ptr = head; !found && (ptr != null); ptr = ptr.next )
        {
          if ( ptr.hasVertex(v) ) // If already present, don't add again
          {
            found = true;
          }
        }

        ptr = head;
        while ( !found && (ptr != null) )
        {
          comp = vname.compareTo( ptr.getFirst().getName() );
          if ( comp < 0 )
          {
            if ( prev == null ) // insert at front - may happen because we're sorting on names
            {
              temp.next = head;
              head = temp;
            }
            else
            {
              temp.next = prev.next;
              prev.next = temp;
            }
            found = true;
          }
          prev = ptr;
          ptr = ptr.next;
        }
        if ( !found ) // Insert at the end of the list
        {
          tail.next = temp;
          tail = temp;
        }
      }
    }
  }


  // Display the state 
  public void display()
  {
    for( Block b = head; b != null; b = b.next )
    {
      System.out.print("[ ");
      b.display();
      if ( b.marked )
      {
        System.out.print("]* ");
      }
      else
      {
        System.out.print("] ");
      }
    }
  }
} // End of the class HNode
