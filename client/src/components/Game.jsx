
const Game = ({
    title,
    dealId,
    salePrice,
    imgsrc,
  }) => {
    return (
      <div className="card text-center mb-3 mr-3" style={{ width: "18rem" }}>
        <div className="center-block mt-3">
        <img className="card-img-top deal-img " src={imgsrc} alt="Card image cap " />
        </div>
        <div className="card-body">
          <h5 className="card-title">{title}</h5>
          <p className="card-text">Cheapest price: {salePrice}$</p>
          <a className="btn btn-warning" href={`https://www.cheapshark.com/redirect?dealID=${dealId}`} >
            {" "}
            Buy{" "}
          </a>
        </div>
      </div>
    );
  };
  
  export default Game;
  