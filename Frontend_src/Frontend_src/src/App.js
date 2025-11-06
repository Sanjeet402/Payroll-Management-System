import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom"; 
import Homepage from "./Homepage";
import PayrollPayments from "./PayrollPayments";
import PayrollManual from "./PayrollManual";
import BatchGrid from "./BatchGrid";
import ApproveHome from "./ApproveHome";
import BatchDetails from "./BatchDetails";
import ProcessedBatches from "./ProcessedBatches";
import Payroll from "./Payroll";
import BatchSummary from "./BatchSummary";
import AccountTransaction from "./AccountTransaction";
import PaymentTransactionPreview from "./PaymentTransactionPreview";
import LandingPage from "./LandingPage";
import LoginPage from "./LoginPage";

function PrivateRoute({ children, roles }) {
  const token = localStorage.getItem("authToken");
  const userRoles = JSON.parse(localStorage.getItem("userRoles") || "[]");

  if (!token) {
    return <Navigate to="/" replace />;
  }

  if (roles && !roles.some(role => userRoles.includes(role))) {
    return <Navigate to="/homepage" replace />; 
  }

  return children;
}


function App() {
  return (
    <Router>
      <Routes>
          <Route path="/" element={<LandingPage />} />
          <Route path="/login" element={<LoginPage />} />

        <Route
          path="/homepage"
          element={
            <PrivateRoute>
              <Homepage />
            </PrivateRoute>
          }
        />

        <Route
          path="/payments/file-upload"
          element={
            <PrivateRoute roles={["OPERATOR"]}>
              <Homepage>
                <PayrollPayments />
              </Homepage>
            </PrivateRoute>
          }
        />

        <Route
          path="/payments/manual"
          element={
            <PrivateRoute roles={["OPERATOR"]}>
              <Homepage>
                <PayrollManual />
              </Homepage>
            </PrivateRoute>
          }
        />

        <Route
          path="/batch-grid"
          element={
            <PrivateRoute roles={["APPROVER"]}>
              <Homepage>
                <BatchGrid />
              </Homepage>
            </PrivateRoute>
          }
          />
            <Route
          path="/approvals/manage"
          element={
            <PrivateRoute roles={["APPROVER","OPERATOR"]}>
              <Homepage>
                <Payroll />
              </Homepage>
            </PrivateRoute>
          }
          />
            <Route
          path="/approvals/approve"
          element={
            <PrivateRoute roles={["APPROVER"]}>
              <Homepage>
                <ApproveHome  />
              </Homepage>
            </PrivateRoute>
          }
        />
         <Route
          path="/processed"
          element={
            <PrivateRoute roles={["APPROVER"]}>
              <Homepage>
                <ProcessedBatches />
              </Homepage>
            </PrivateRoute>
          }
        />
        <Route
  path="/approvals/batches/:batchesRef"
  element={
    <PrivateRoute roles={["APPROVER","OPERATOR"]}>
      <Homepage>
        <BatchSummary />
      </Homepage>
    </PrivateRoute>
  }
/>
        <Route
  path="/approvals/batch/:id"
  element={
    <PrivateRoute roles={["APPROVER","OPERATOR"]}>
      <Homepage>
        <BatchDetails />
      </Homepage>
    </PrivateRoute>
  }
/>
<Route
  path="/approvals/processed"
  element={
    <PrivateRoute roles={["APPROVER"]}>
      <Homepage>
        <ProcessedBatches />
      </Homepage>
    </PrivateRoute>
  }
/>
<Route
  path="/reports"
  element={
    <PrivateRoute roles={["APPROVER","OPERATOR"]}>
      <Homepage>
        <AccountTransaction />
      </Homepage>
    </PrivateRoute>
  }
/>

    <Route
  path="/statements"
  element={
    <PrivateRoute roles={["OPERATOR", "APPROVER"]}>
      <Homepage>
        <PaymentTransactionPreview />
      </Homepage>
    </PrivateRoute>
  }
/>    
        <Route path="*" element={<Navigate to="/homepage" replace />} />

      </Routes>
    </Router>
  );
}

export default App;