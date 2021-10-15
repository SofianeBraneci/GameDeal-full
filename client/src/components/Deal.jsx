import { Link } from "react-router-dom";

const Deal = ({
  title,
  dealId,
  salePrice,

  imgsrc,
}) => {
  return (
    <div className="card text-center mb-3 mr-3" style={{ width: "18rem" }}>
      <div className="center-block mt-3">
        <img
          className="card-img-top deal-img "
          src={imgsrc}
          alt="Card image cap "
        />
      </div>
      <div className="card-body">
        <h5 className="card-title">{title}</h5>
        <p className="card-text">Price: {salePrice}$</p>
        <Link className="btn btn-warning" to={`/deals/${dealId}`}>
          {" "}
          Details{" "}
        </Link>
      </div>
    </div>
  );
};

export default Deal;
