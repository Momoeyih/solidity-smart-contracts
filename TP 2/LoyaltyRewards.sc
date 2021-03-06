pragma solidity ^0.4.18;

import './EIP20Interface.sol';
contract Rewards is EIP20Interface {
    
    uint public constant totalPoints = 1000000;
    
    mapping(address=>uint256) soldes;
    mapping(address=>mapping(address=>uint256)) allowed;
    
    function Rewards (uint _end, uint _amountToRaise)public
    {
        soldes[msg.sender]= totalPoints;
    }    
    
    function totalSupply() public constant returns (uint256 totalSupply)
    {
        return totalPoints;
    }
    
    function balanceOf(address _owner) public view returns (uint256 solde)
    {
        return soldes[_owner];
    }
    
    function transfer(address _to, uint256 _value) public returns (bool success)
    {
        require(soldes[msg.sender] >= _value && _value>0);
        soldes[msg.sender] = soldes[msg.sender].sub(_value);
        soldes[_to] = soldes[_to].add(_value);
        Transfer(msg.sender,_to,_value);
        return true;
    }
    
    function transferFrom(address _from, address _to, uint256 _value) public returns (bool success){
        require(
            allowed[_from][msg.sender]>=_value
            &&soldes[_from]>=_value
            &&_value>0
                );
        soldes[_from]=soldes[_from].sub(_value);
        soldes[_to]=soldes[_to].add(_value);
        allowed[_from][msg.sender]=allowed[_from][msg.sender].sub(_value);
        Transfer(_from,_to,_value);
        return true;
    }
    function approve(address _spender, uint256 _value) public returns (bool success){
        allowed[msg.sender][_spender]=_value;
        Approval(msg.sender,_spender,_value);
        return true;
    }
    function allowance(address _owner, address _spender) public view returns (uint256 remaining){
        return allowed[_owner][_spender];
    }
    event Transfer(address indexed _from, address indexed _to, uint256 _value); 
    event Approval(address indexed _owner, address indexed _spender, uint256 _value);

   //Fonction KILL
   function kill() isowner()
       {
           delete totalPoints;
           delete soldes;
           delete allowed;
           selfdestruct(msg.sender);
       }
}
