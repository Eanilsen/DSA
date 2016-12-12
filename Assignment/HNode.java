// Class file for the nodes used by the OBDDH diagram.
 

import java.util.*; // For testing

class HNode
{
  private FloatingPoint reliability;
  private HState state;

  // The default constructor creates the root node of the OBDDH diagram
  public HNode( Vertex source ) 
  {
    if ( source == null )
    {
      throw new IllegalArgumentException("Argument passed to HNode constructor must not be null.");
    }
    reliability = new FloatingPoint( 1.0 );
    state = new HState( source );
  }

  // The copy constructor is used for positive child nodes
  public HNode( HNode n )
  {
    reliability = new FloatingPoint( n.reliability );
    state = new HState( n.state );
  }

  // Returns true if the given node is equal (isomorphic) to the current node
  public boolean isEqual( HNode node )
  {
    return state.isEqual( node.state ); 
  }

  // Merges the current node into the provided node. The current node should not be used further after this.
  public void mergeInto( HNode node )
  {
    node.reliability.add( reliability );        // Increment the reliability of the other node
  }

  // Creates a new node that is the positive child of the current one
  public HNode makePos( Vertex v, Edge elist[] ) 
  {
    int index;

    if ( v == null )
    {
      throw new IllegalArgumentException("Argument passed to makePos must not be null.");
    }

    HNode pos = new HNode( this );  // Create a HNode that's a copy of this one

    // Multiply by this vertices's reliability to get the child reliability
    pos.reliability.multiply( v.getRel() );

    // For every adjacent edge, contract it
    for ( index = 0; index < elist.length; index++ )
    {
        pos.state.contract( elist[index] );
    }
    pos.state.check( v );
    return pos;
  }

  // Turns the current node into its own negative child. This is done as an optimization since the
  // node would otherwise be discarded after the negative child was created.
  public void makeNeg( Vertex v, Edge elist[] ) 
  {
    int index;

    if ( v == null )
    {
      throw new IllegalArgumentException("Argument passed to makeNeg must not be null.");
    }

    // Get the reliability of the edge
    FloatingPoint factor = new FloatingPoint( v.getRel() );

    // For every adjacent edge, delete it
    for ( index = 0; index < elist.length; index++ )
    {
      state.delete( elist[index] );	  // Update the state of the positive child
    }
    state.check( v );

    factor.invert();      // Invert it for the chance of failure

    // Calculate the probability of the negative child 
    reliability.multiply( factor );  
  }

//***********//
// Accessors //
//***********//

  // Returns true only if the node is successful. A node is considered successful if it represents a state where the source
  // is connected to the target
  public boolean isSuccess()
  {
    return state.success;
  } 

  // Returns true only if the node is failed. A node is considered failed if it represents a state of the network where the
  // source is unable to connect to the target
  public boolean isFailed() 
  {
    return state.failure;
  }

  // Returns the reliability of the node
  public FloatingPoint getRel() 
  {
    return reliability;
  }

  // Return a numerical code that describes the node. Can be used to generate a hash.
  public int getCode()
  {
    return 1;
  }

// Note that there are no direct set mutators since there is no legitimate need for them.

  // Display the node
  public void display()
  {
    System.out.print("<");
    state.display();
    System.out.print(">");
  }
} // End of the class HNode
