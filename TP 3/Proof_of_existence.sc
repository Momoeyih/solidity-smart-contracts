pragma solidity ^0.4.18;


contract BlockizorAuthenticity
{  
    string public hash;
    address public owner;
    
     function BlockizorAuthenticity(string _hash) 
    {
        hash = _hash;
        owner = msg.sender;
    }
    
    modifier isowner()
    {
       if(msg.sender!=owner)
       {
       throw;
       _;
       }
    }
    function kill() isowner()
      {
           delete owner;
           selfdestruct(msg.sender);
       }
}
